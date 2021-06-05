package org.safegyn.service;

import org.safegyn.api.DoctorApi;
import org.safegyn.api.SubmissionApi;
import org.safegyn.db.entity.Doctor;
import org.safegyn.model.data.DoctorData;
import org.safegyn.model.error.ApiException;
import org.safegyn.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = ApiException.class)
public class DoctorService {

    @Autowired
    private DoctorApi doctorApi;

    @Autowired
    private SubmissionApi submissionApi;

    public List<DoctorData> search(String city, String gender, Integer overallRating, Integer professionalRating,
                                   Integer objectiveRating, Integer respectfulRating, Integer anonymityRating) throws ApiException {
        List<Doctor> results = doctorApi.search(city, gender, overallRating, professionalRating, objectiveRating, respectfulRating, anonymityRating);
        return getDoctorData(results);
    }

    private DoctorData getDoctorData(Doctor doctor) throws ApiException {
        DoctorData doctorData = ConvertUtil.convert(doctor, DoctorData.class);
        doctorData.setOverallRating(getOverallRating(doctorData));
        return doctorData;
    }

    private List<DoctorData> getDoctorData(List<Doctor> results) throws ApiException {
        List<DoctorData> data = ConvertUtil.convert(results, DoctorData.class);
        for (DoctorData doctorData : data) {
            doctorData.setOverallRating(getOverallRating(doctorData));
        }
        return data;
    }

    private double getOverallRating(DoctorData doctorData) {
        double totalScore = doctorData.getAnonymityScore() +
                doctorData.getInclusivityScore() +
                doctorData.getRespectfulnessScore() +
                doctorData.getProfessionalismScore() +
                doctorData.getObjectivityScoreCount();

        int totalSubmissions = doctorData.getAnonymityScoreCount() +
                doctorData.getInclusivityScoreCount() +
                doctorData.getRespectfulnessScoreCount() +
                doctorData.getProfessionalismScoreCount() +
                doctorData.getObjectivityScoreCount();

        return totalScore/totalSubmissions;
    }

    public DoctorData getCheck(Long doctorId) throws ApiException {
        return getDoctorData(doctorApi.getCheck(doctorId));
    }

}
