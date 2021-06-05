package org.safegyn.service;

import org.safegyn.api.DoctorApi;
import org.safegyn.api.QuestionApi;
import org.safegyn.api.ReviewApi;
import org.safegyn.api.SubmissionApi;
import org.safegyn.db.entity.Doctor;
import org.safegyn.db.entity.Question;
import org.safegyn.db.entity.Review;
import org.safegyn.db.entity.Submission;
import org.safegyn.model.error.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(rollbackFor = ApiException.class)
public class ReviewService {

    @Autowired
    private ReviewApi reviewApi;

    @Autowired
    private SubmissionApi submissionApi;

    @Autowired
    private QuestionApi questionApi;

    @Autowired
    private DoctorApi doctorApi;

    private Submission addAndGetSubmission(Map<String, String> reviewMap) throws ApiException {
        Doctor doctor = doctorApi.getOrPostDoctor(reviewMap);

        Submission submission = new Submission();
        submission.setDoctorId(doctor.getId());
        submissionApi.add(submission);

        return submission;
    }

    public List<Review> getReviewsBySubmissionId(Long submissionId) {
        return reviewApi.getCheckBySubmissionId(submissionId);
    }

    public void addReview(Map<String, String> reviewMap) throws ApiException {
        Submission submission = addAndGetSubmission(reviewMap);

        List<Review> reviews = new ArrayList<>();
        for (Map.Entry<String, String> entry : reviewMap.entrySet()) {
            Question question = questionApi.getCheck(entry.getKey());
            Review review = getReview(submission.getId(), entry.getValue(), question);

            reviews.add(review);
        }

        updateScores(submission.getDoctorId(), reviews);
        reviewApi.add(reviews);
    }

    private void updateScores(Long doctorId, List<Review> reviews) throws ApiException {
        Doctor doctor = doctorApi.getCheck(doctorId);

        for (Review review : reviews) {
            Question question = questionApi.getCheck(review.getId());
            Double score = review.getScore();

            if (Objects.nonNull(score)) continue;

            if (question.getCategory().equals(Question.Category.ANONYMITY)) {
                doctor.setAnonymityScore(doctor.getAnonymityScore() + review.getScore());
                doctor.setAnonymityScoreCount(doctor.getAnonymityScoreCount() + 1);
            }

            if (question.getCategory().equals(Question.Category.INCLUSIVITY)) {
                doctor.setInclusivityScore(doctor.getInclusivityScore() + review.getScore());
                doctor.setInclusivityScoreCount(doctor.getInclusivityScoreCount() + 1);
            }

            if (question.getCategory().equals(Question.Category.OBJECTIVITY)) {
                doctor.setObjectivityScore(doctor.getObjectivityScore() + review.getScore());
                doctor.setObjectivityScoreCount(doctor.getObjectivityScoreCount() + 1);
            }

            if (question.getCategory().equals(Question.Category.PROFESSIONALISM)) {
                doctor.setProfessionalismScore(doctor.getProfessionalismScore() + review.getScore());
                doctor.setProfessionalismScoreCount(doctor.getProfessionalismScoreCount() + 1);
            }

            if (question.getCategory().equals(Question.Category.RESPECTFULNESS)) {
                doctor.setRespectfulnessScore(doctor.getRespectfulnessScore() + review.getScore());
                doctor.setRespectfulnessScoreCount(doctor.getRespectfulnessScoreCount() + 1);
            }

        }
    }

    private Review getReview(Long submissionId, String answer, Question question) {
        Review review = new Review();
        review.setSubmissionId(submissionId);
        review.setQuestionId(question.getId());
        review.setAnswer(answer);
        review.setScore(getScaledScore(1, 4, getScore(answer)));
        return review;
    }

    private Double getScaledScore(int minScore, int maxScore, Integer score) {
        if (Objects.isNull(score)) return null;
        return (5 / (double) (maxScore - minScore)) * (score - minScore);
    }

    private Integer getScore(String answer) {

        if (answer.equals("I DON'T KNOW")) return null;
        if (answer.equals("SADLY, NOPE")) return 0;
        if (answer.equals("PROBABLY NOT")) return 1;
        if (answer.equals("MAYBE")) return 2;
        if (answer.equals("PROBABLY YES")) return 3;
        if (answer.equals("HAPPILY, YEP")) return 4;
        return null;
    }

}
