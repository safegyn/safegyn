package org.safegyn.dto;

import org.safegyn.model.data.DoctorData;
import org.safegyn.model.data.DoctorSearchData;
import org.safegyn.model.error.ApiException;
import org.safegyn.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Transactional(rollbackFor = ApiException.class)
public class DoctorDto {

    @Autowired
    private DoctorService doctorService;

    public DoctorSearchData search(String city, String gender, Integer overallRating, Integer professionalRating,
                                   Integer objectiveRating, Integer respectfulRating, Integer anonymityRating, Integer inclusivityRating) throws ApiException {
        city = city.trim().toUpperCase(Locale.ROOT);
        return doctorService.search(city, gender, overallRating, professionalRating, objectiveRating, respectfulRating, anonymityRating, inclusivityRating);
    }

    public DoctorData getCheck(Long doctorId) throws ApiException {
        return doctorService.getCheck(doctorId);
    }

    public List<String> getCities() {
        return doctorService.getCities();
    }
}
