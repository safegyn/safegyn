package org.safegyn.db.dao;

import org.safegyn.db.entity.Doctor;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Repository
public class DoctorDao extends AbstractDao<Doctor> {

    public Doctor getByNameAndCity(String name, String city) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        CriteriaQuery<Doctor> query = cb.createQuery(Doctor.class);
        Root<Doctor> root = query.from(Doctor.class);

        query.where(cb.and(
                cb.equal(root.get("name"), name)),
                cb.equal(root.get("city"), city));
        TypedQuery<Doctor> tQuery = createQuery(query);

        return selectSingleOrNull(tQuery);
    }

    public List<Doctor> search(String city, String gender, Integer overallRating, Integer professionalRating,
                               Integer objectiveRating, Integer respectfulRating, Integer anonymityRating) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Doctor> criteriaQuery = cb.createQuery(Doctor.class);
        Root<Doctor> root = criteriaQuery.from(Doctor.class);

        Predicate predicate = cb.and();
        predicate = cb.and(predicate, cb.equal(root.get("city"), city));

        if (Objects.nonNull(gender)) predicate = cb.and(predicate, cb.equal(root.get("gender"), gender));
        if (Objects.nonNull(overallRating))
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("overallRating"), overallRating));
        if (Objects.nonNull(professionalRating))
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("professionalRating"), professionalRating));
        if (Objects.nonNull(objectiveRating))
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("objectiveRating"), objectiveRating));
        if (Objects.nonNull(respectfulRating))
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("respectfulRating"), respectfulRating));
        if (Objects.nonNull(anonymityRating))
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("anonymityRating"), anonymityRating));

        return createQuery(criteriaQuery.where(predicate)).getResultList();
    }

}
