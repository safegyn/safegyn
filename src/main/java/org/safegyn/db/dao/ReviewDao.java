package org.safegyn.db.dao;

import org.safegyn.db.entity.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ReviewDao extends AbstractDao<Review> {

    public Review selectBySubmissionIdAndQuestionId(Long submissionId, Long questionId) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.where
                (cb.and(cb.equal(root.get("submissionId"), submissionId)),
                        (cb.equal(root.get("questionId"), questionId)));
        TypedQuery<Review> tQuery = createQuery(query);
        return selectSingleOrNull(tQuery);
    }

    public List<Review> selectByDoctorIdAndQuestionId(Long doctorId, Long questionId) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<Review> query = cb.createQuery(Review.class);
        Root<Review> root = query.from(Review.class);
        query.where
                (cb.and(cb.equal(root.get("doctorId"), doctorId)),
                        (cb.equal(root.get("questionId"), questionId)));
        TypedQuery<Review> tQuery = createQuery(query);
        return selectMultiple(tQuery);
    }

}
