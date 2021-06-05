package org.safegyn.dto;

import org.safegyn.db.entity.Review;
import org.safegyn.db.entity.Submission;
import org.safegyn.model.data.DoctorReviewData;
import org.safegyn.model.data.ReviewData;
import org.safegyn.model.error.ApiException;
import org.safegyn.service.ReviewService;
import org.safegyn.service.SubmissionService;
import org.safegyn.util.ConvertUtil;
import org.safegyn.util.NormalizeUtil;
import org.safegyn.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = ApiException.class)
public class ReviewDto {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SubmissionService submissionService;

    public void addReview(Map<String, List<Map<String, String>>> cityToQuestionAnswerMaps) throws ApiException {
        for (Map.Entry<String, List<Map<String, String>>> entry : cityToQuestionAnswerMaps.entrySet()) {
            String city = entry.getKey().trim().toUpperCase(Locale.ROOT);
            for (Map<String, String> reviewMap : entry.getValue()) {
                reviewMap.put("city", city);
                reviewMap = NormalizeUtil.normalizeReviewMap(reviewMap);
                ValidationUtil.validateAddReviewMap(reviewMap);
                reviewService.addReview(reviewMap);
            }
        }
    }

    public ReviewData getReviewsBySubmissionId(Long submissionId) throws ApiException {
        List<Review> reviews = reviewService.getReviewsBySubmissionId(submissionId);
        Map<Long, String> reviewMap = reviews.stream().collect(Collectors.toMap(Review::getId, Review::getAnswer));

        Submission submission = submissionService.getCheck(submissionId);

        return getReviewData(reviewMap, submission);
    }

    public Map<Long, DoctorReviewData> getReviewsByDoctorId(Long doctorId) throws ApiException {
        List<Submission> submissions = submissionService.getByDoctorId(doctorId);

        Map<Long, DoctorReviewData> map = new HashMap<>();
        for (Submission submission : submissions) {
            ReviewData reviewData = getReviewsBySubmissionId(submission.getId());
            map.put(submission.getId(), ConvertUtil.convert(reviewData, DoctorReviewData.class));
        }

        return map;
    }

    private ReviewData getReviewData(Map<Long, String> reviewMap, Submission submission) {
        ReviewData data = new ReviewData();
        data.setSubmissionId(submission.getId());
        data.setReviewMap(reviewMap);
        return data;
    }
}
