package org.safegyn.api;

import org.safegyn.db.dao.DoctorDao;
import org.safegyn.db.entity.Doctor;
import org.safegyn.model.error.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class DoctorApi extends AbstractApi {

    @Autowired
    private DoctorDao dao;

    public Doctor add(Map<String, String> reviewMap) throws ApiException {
        Doctor doctor = populateAndGetNewDoctor(reviewMap);
        dao.persist(doctor);
        return doctor;
    }

    public Doctor getCheck(Long id) throws ApiException {
        Doctor doctor = dao.select(id);
        checkNotNull(doctor,
                ApiException.Type.USER_ERROR,
                "Fetching Doctor by user ID",
                "Doctor does not exist with doctor ID: " + id,
                "Invalid Doctor ID");
        return doctor;
    }

    public Doctor getOrPostDoctor(Map<String, String> reviewMap) throws ApiException {
        String name = reviewMap.get("NAME");
        String city = reviewMap.get("CITY");
        Doctor doctor = dao.getByNameAndCity(name, city);

        if (Objects.isNull(doctor)) return add(reviewMap);
        else return patch(doctor, reviewMap);
    }

    private Doctor populateAndGetNewDoctor(Map<String, String> reviewMap) {
        Doctor doctor = new Doctor();
        doctor.setName(reviewMap.get("NAME"));
        doctor.setCity(reviewMap.get("CITY"));
        /* Non-necessary fields */
        doctor.setGender(reviewMap.get("GENDER"));
        doctor.setAgeRange(reviewMap.get("AGE-RANGE"));
        doctor.setOfficeAddress(reviewMap.get("OFFICE ADDRESS"));
        doctor.setTelNo(reviewMap.get("OFFICE TEL. NO."));
        doctor.setConsultationFees(reviewMap.get("STANDARD CONSULTATION FEES (INR)"));
        doctor.setPaymentForms(reviewMap.get("WHAT FORMS OF PAYMENT DO THEY ACCEPT?"));
        doctor.setOfficeHours(reviewMap.get("OFFICE HOURS"));
        doctor.setLanguagesSpoken(reviewMap.get("LANGUAGE(S) SPOKEN"));
        doctor.setRatingCount(1);

        return doctor;
    }

    private Doctor patch(Doctor doctor, Map<String, String> reviewMap) {
        doctor.setRatingCount(doctor.getRatingCount() + 1);
        if (Objects.isNull(doctor.getGender())) doctor.setGender(reviewMap.get("GENDER"));
        if (Objects.isNull(doctor.getAgeRange())) doctor.setAgeRange(reviewMap.get("AGE-RANGE"));
        if (Objects.isNull(doctor.getOfficeAddress())) doctor.setOfficeAddress(reviewMap.get("OFFICE ADDRESS"));
        if (Objects.isNull(doctor.getTelNo())) doctor.setTelNo(reviewMap.get("OFFICE TEL. NO."));
        if (Objects.isNull(doctor.getConsultationFees()))
            doctor.setConsultationFees(reviewMap.get("STANDARD CONSULTATION FEES (INR)"));
        if (Objects.isNull(doctor.getPaymentForms()))
            doctor.setPaymentForms(reviewMap.get("WHAT FORMS OF PAYMENT DO THEY ACCEPT?"));
        if (Objects.isNull(doctor.getOfficeHours())) doctor.setOfficeHours(reviewMap.get("OFFICE HOURS"));
        if (Objects.isNull(doctor.getLanguagesSpoken())) doctor.setLanguagesSpoken(reviewMap.get("LANGUAGE(S) SPOKEN"));
        return doctor;
    }

    public List<Doctor> search(String city, String gender) {
        return dao.search(city, gender);
    }

    public List<String> getCities() {
        return dao.getCities();
    }

}


