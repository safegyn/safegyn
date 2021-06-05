package org.safegyn.api;

import org.safegyn.db.dao.ReviewDao;
import org.safegyn.db.entity.Review;
import org.safegyn.model.error.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewApi extends AbstractApi {

    @Autowired
    private ReviewDao dao;

    public void add(Review review) throws ApiException {
        validateAddition(review);
        dao.persist(review);
    }

    public void add(List<Review> reviews) throws ApiException {
        for (Review review : reviews) {
            validateAddition(review);
            dao.persist(review);
        }
    }

    private void validateAddition(Review review) throws ApiException {
        Review exists = dao.selectBySubmissionIdAndQuestionId(review.getSubmissionId(), review.getQuestionId());
        checkNull(exists,
                ApiException.Type.USER_ERROR,
                "Adding review item",
                "Review item already exists for submission:" + review.getSubmissionId() +
                        ", question:" + review.getQuestionId(),
                "Ensure the question hasn't already been added");
    }

    public Review getCheck(Long id) throws ApiException {
        Review review = dao.select(id);
        checkNotNull(review,
                ApiException.Type.USER_ERROR,
                "Getting review by ID",
                "No review exists with ID:" + id,
                "Double check the ID used for lookup");

        return review;
    }

    public List<Review> getCheckBySubmissionId(Long submissionId) {
        return dao.selectMultiple("submissionId", submissionId);
    }

}


