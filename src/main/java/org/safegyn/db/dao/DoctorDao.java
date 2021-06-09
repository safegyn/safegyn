package org.safegyn.db.dao;

import org.safegyn.db.entity.Doctor;
import org.safegyn.db.entity.Review;
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

    public List<String> getCities() {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<String> query = criteriaBuilder.createQuery(String.class);
        Root<Doctor> root = query.from(Doctor.class);
        query.select(root.get("city")).distinct(true);
        return this.em.createQuery(query).getResultList();
    }

    public List<Doctor> search(String city, String gender) {
        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Doctor> criteriaQuery = cb.createQuery(Doctor.class);
        Root<Doctor> root = criteriaQuery.from(Doctor.class);

        Predicate predicate = cb.and();
        predicate = cb.and(predicate, cb.equal(root.get("city"), city));
        if (Objects.nonNull(gender)) predicate = cb.and(predicate, cb.equal(root.get("gender"), gender));

        return createQuery(criteriaQuery.where(predicate)).getResultList();
    }
}
