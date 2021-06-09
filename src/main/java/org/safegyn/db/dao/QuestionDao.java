package org.safegyn.db.dao;

import org.safegyn.db.entity.Question;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class QuestionDao extends AbstractDao<Question> {

    public List<Question> get(List<Long> ids) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<Question> query = cb.createQuery(Question.class);
        Root<Question> root = query.from(Question.class);
        query.where(root.get("id").in(ids));
        return selectMultiple(createQuery(query));
    }

}
