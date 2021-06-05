package org.safegyn.dto;

import org.safegyn.model.data.DoctorData;
import org.safegyn.model.error.ApiException;
import org.safegyn.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = ApiException.class)
public class DoctorDto {

    @Autowired
    private DoctorService doctorService;

    public List<DoctorData> search(String city, String gender, Integer overallRating, Integer professionalRating,
                                   Integer objectiveRating, Integer respectfulRating, Integer anonymityRating) throws ApiException {
        return doctorService.search(city, gender, overallRating, professionalRating, objectiveRating, respectfulRating, anonymityRating);
    }

    public DoctorData getCheck(Long doctorId) throws ApiException {
        return doctorService.getCheck(doctorId);
    }
}
