package org.safegyn.service;

import org.safegyn.api.DoctorApi;
import org.safegyn.api.SubmissionApi;
import org.safegyn.db.entity.Doctor;
import org.safegyn.model.data.DoctorData;
import org.safegyn.model.data.DoctorSearchData;
import org.safegyn.model.error.ApiException;
import org.safegyn.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackFor = ApiException.class)
public class DoctorService {

    @Autowired
    private DoctorApi doctorApi;

    @Autowired
    private SubmissionApi submissionApi;

    public DoctorSearchData search(String city, String gender, Integer overallRating, Integer professionalRating,
                                   Integer objectiveRating, Integer respectfulRating, Integer anonymityRating, Integer inclusivityRating) throws ApiException {
        List<Doctor> results = doctorApi.search(city, gender);

        List<Doctor> filterByRatings = getFilterByOverallRating(overallRating, results);
        filterByRatings = getFilterByRatings(filterByRatings, professionalRating, objectiveRating,
                respectfulRating, anonymityRating, inclusivityRating);

        return getDoctorData(city, filterByRatings);
    }

    private List<Doctor> getFilterByRatings(List<Doctor> doctors, Integer professionalRating, Integer objectiveRating,
                                            Integer respectfulRating, Integer anonymityRating, Integer inclusivityRating) {
        List<Doctor> filteredList = new ArrayList<>();

        if (Objects.isNull(professionalRating)) professionalRating = 0;
        if (Objects.isNull(objectiveRating)) objectiveRating = 0;
        if (Objects.isNull(respectfulRating)) respectfulRating = 0;
        if (Objects.isNull(anonymityRating)) anonymityRating = 0;
        if (Objects.isNull(inclusivityRating)) inclusivityRating = 0;

        for (Doctor doctor : doctors) {
            if ((doctor.getProfessionalismScoreCount() > 0 && doctor.getProfessionalismScore() / doctor.getProfessionalismScoreCount() > professionalRating) &&
                    (doctor.getObjectivityScoreCount() > 0 && doctor.getObjectivityScore() / doctor.getObjectivityScoreCount() > objectiveRating) &&
                    (doctor.getRespectfulnessScoreCount() > 0 && doctor.getRespectfulnessScore() / doctor.getRespectfulnessScoreCount() > respectfulRating) &&
                    (doctor.getAnonymityScoreCount() > 0 && doctor.getAnonymityScore() / doctor.getAnonymityScoreCount() > anonymityRating) &&
                    (doctor.getInclusivityScoreCount() > 0 && doctor.getInclusivityScore() / doctor.getInclusivityScoreCount() > inclusivityRating))
                filteredList.add(doctor);
        }

        return filteredList;
    }

    private List<Doctor> getFilterByOverallRating(Integer overallRating, List<Doctor> results) throws ApiException {
        if (Objects.isNull(overallRating)) overallRating = 0;

        List<Doctor> filterByOverallRating = new ArrayList<>();
        for (Doctor doctor : results)
            if (getDoctorData(doctor).getOverallRating() >= overallRating)
                filterByOverallRating.add(doctor);
        return filterByOverallRating;
    }

    private DoctorData getDoctorData(Doctor doctor) throws ApiException {
        DoctorData doctorData = ConvertUtil.convert(doctor, DoctorData.class);
        doctorData.setOverallRating(getOverallRating(doctorData));
        return doctorData;
    }

    private DoctorSearchData getDoctorData(String city, List<Doctor> results) throws ApiException {
        List<DoctorData> doctorDataList = ConvertUtil.convert(results, DoctorData.class);
        for (DoctorData doctorData : doctorDataList)
            doctorData.setOverallRating(getOverallRating(doctorData));

        DoctorSearchData data = new DoctorSearchData();
        data.setCity(city);
        data.setTotal(results.size());
        data.setReviews(doctorDataList);
        return data;
    }

    private double getOverallRating(DoctorData doctorData) {
        double totalScore = doctorData.getAnonymityScore() +
                doctorData.getInclusivityScore() +
                doctorData.getRespectfulnessScore() +
                doctorData.getProfessionalismScore() +
                doctorData.getObjectivityScore();

        int totalSubmissions = doctorData.getAnonymityScoreCount() +
                doctorData.getInclusivityScoreCount() +
                doctorData.getRespectfulnessScoreCount() +
                doctorData.getProfessionalismScoreCount() +
                doctorData.getObjectivityScoreCount();

        if (totalSubmissions == 0) return 0;

        return totalScore / totalSubmissions;
    }

    public DoctorData getCheck(Long doctorId) throws ApiException {
        return getDoctorData(doctorApi.getCheck(doctorId));
    }

    public List<String> getCities() {
        return doctorApi.getCities();
    }
}
