package org.safegyn.dto;

import org.safegyn.db.entity.Question;
import org.safegyn.db.entity.Review;
import org.safegyn.db.entity.Submission;
import org.safegyn.model.data.DoctorData;
import org.safegyn.model.data.DoctorReviewData;
import org.safegyn.model.data.ReviewData;
import org.safegyn.model.data.ScoreDistribution;
import org.safegyn.model.error.ApiException;
import org.safegyn.service.DoctorService;
import org.safegyn.service.QuestionService;
import org.safegyn.service.ReviewService;
import org.safegyn.service.SubmissionService;
import org.safegyn.util.ConvertUtil;
import org.safegyn.util.NormalizeUtil;
import org.safegyn.util.StringUtil;
import org.safegyn.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = ApiException.class)
public class ReviewDto {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private QuestionService questionService;

    public void addReview(Map<String, List<Map<String, String>>> cityToQuestionAnswerMaps) throws ApiException {
        for (Map.Entry<String, List<Map<String, String>>> entry : cityToQuestionAnswerMaps.entrySet()) {
            String city = entry.getKey().trim().toUpperCase();
            addReview(entry.getValue(), city);
        }
    }

    private void addReview(List<Map<String, String>> reviewContents, String city) throws ApiException {
        for (Map<String, String> reviewMap : reviewContents) {
            reviewMap.put("city", city);
            reviewMap = NormalizeUtil.normalizeReviewMap(reviewMap);
            ValidationUtil.validateAddReviewMap(reviewMap);

            reviewService.addReview(reviewMap);
        }
    }

    public ReviewData getReviewsBySubmissionId(Submission submission) throws ApiException {
        List<Review> reviews = reviewService.getReviewsBySubmissionId(submission.getId());
        DoctorData doctor = doctorService.getCheck(submission.getDoctorId());


        List<Question> questions = questionService.getCheck(reviews.stream().map(Review::getQuestionId).collect(Collectors.toList()));
        Map<Long, Question> questionMap = questions.stream().collect(Collectors.toMap(Question::getId, Function.identity()));

        return getReviewData(reviews, doctor, submission, questionMap);
    }

    public List<DoctorReviewData> getReviewsByDoctorId(Long doctorId) throws ApiException {
        List<Submission> submissions = submissionService.getByDoctorId(doctorId);

        List<DoctorReviewData> dataList = new ArrayList<>();
        for (Submission submission : submissions) {
            ReviewData reviewData = getReviewsBySubmissionId(submission);
            dataList.add(ConvertUtil.convert(reviewData, DoctorReviewData.class));
        }

        return dataList;
    }

    private ReviewData getReviewData(List<Review> reviews, DoctorData doctor, Submission submission, Map<Long, Question> questionMap) throws ApiException {
        ReviewData data = new ReviewData();
        data.setSubmissionId(submission.getId());
        data.setDoctorId(doctor.getId());
        setScoresForSubmission(data, reviews);

        data.setAnswers(new ArrayList<>());
        for (Review review : reviews) {
            if (StringUtil.isEmpty(review.getAnswer()) || review.getAnswer().equals("I DON'T KNOW")) continue;

            ReviewData.ReviewSubmissionEntry entry = new ReviewData.ReviewSubmissionEntry();
            entry.setTitle(questionMap.get(review.getQuestionId()).getTitle());
            entry.setAnswer(review.getAnswer());
            entry.setCategory(questionMap.get(review.getQuestionId()).getCategory());
            data.getAnswers().add(entry);
        }

        return data;
    }

    private void setScoresForSubmission(ReviewData data, List<Review> reviews) throws ApiException {
        ScoreDistribution scores = getScoreDistribution(reviews);
        data.setProfessionalismScore(scores.getProfessionalismScore());
        data.setProfessionalismScoreCount(scores.getProfessionalismScoreCount());
        data.setObjectivityScore(scores.getObjectivityScore());
        data.setObjectivityScoreCount(scores.getObjectivityScoreCount());
        data.setRespectfulnessScore(scores.getRespectfulnessScore());
        data.setRespectfulnessScoreCount(scores.getRespectfulnessScoreCount());
        data.setAnonymityScore(scores.getAnonymityScore());
        data.setAnonymityScoreCount(scores.getAnonymityScoreCount());
        data.setInclusivityScore(scores.getInclusivityScore());
        data.setInclusivityScoreCount(scores.getInclusivityScoreCount());
        data.setAverageRating(getAverageRating(scores));
    }

    private Double getAverageRating(ScoreDistribution scores) {
        Double totalScore = scores.getAnonymityScore() +
                scores.getInclusivityScore() +
                scores.getObjectivityScore() +
                scores.getRespectfulnessScore() +
                scores.getProfessionalismScore();

        Integer totalCount = scores.getAnonymityScoreCount() +
                scores.getInclusivityScoreCount() +
                scores.getObjectivityScoreCount() +
                scores.getRespectfulnessScoreCount() +
                scores.getProfessionalismScoreCount();

        if (totalCount == 0) return null;
        return totalScore / totalCount;
    }

    private ScoreDistribution getScoreDistribution(List<Review> reviews) throws ApiException {
        List<Long> questionIds = reviews.stream().map(Review::getQuestionId).collect(Collectors.toList());
        Map<Long, Question> questionMap = questionService.getCheck(questionIds).stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        ScoreDistribution scoreDistribution = new ScoreDistribution();
        for (Review review : reviews) {
            if (questionMap.containsKey(review.getQuestionId())) {
                Question question = questionMap.get(review.getQuestionId());
                populateScores(scoreDistribution, review, question);
            }
        }
        return scoreDistribution;
    }

    private void populateScores(ScoreDistribution scoreDistribution, Review review, Question question) {
        if (Objects.isNull(review.getScore())) return;

        if (question.getCategory().equals(Question.Category.ANONYMITY)) {
            scoreDistribution.setAnonymityScore(scoreDistribution.getAnonymityScore() + review.getScore());
            scoreDistribution.setAnonymityScoreCount(scoreDistribution.getAnonymityScoreCount() + 1);
        }
        if (question.getCategory().equals(Question.Category.INCLUSIVITY)) {
            if (Objects.isNull(review.getScore())) return;
            scoreDistribution.setInclusivityScore(scoreDistribution.getInclusivityScore() + review.getScore());
            scoreDistribution.setInclusivityScoreCount(scoreDistribution.getInclusivityScoreCount() + 1);
        }
        if (question.getCategory().equals(Question.Category.OBJECTIVITY)) {
            scoreDistribution.setObjectivityScore(scoreDistribution.getObjectivityScore() + review.getScore());
            scoreDistribution.setObjectivityScoreCount(scoreDistribution.getObjectivityScoreCount() + 1);
        }
        if (question.getCategory().equals(Question.Category.PROFESSIONALISM)) {
            scoreDistribution.setProfessionalismScore(scoreDistribution.getProfessionalismScore() + review.getScore());
            scoreDistribution.setProfessionalismScoreCount(scoreDistribution.getProfessionalismScoreCount() + 1);
        }
        if (question.getCategory().equals(Question.Category.RESPECTFULNESS)) {
            scoreDistribution.setRespectfulnessScore(scoreDistribution.getRespectfulnessScore() + review.getScore());
            scoreDistribution.setRespectfulnessScoreCount(scoreDistribution.getRespectfulnessScoreCount() + 1);
        }
    }

}
