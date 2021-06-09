package org.safegyn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.safegyn.dto.DoctorDto;
import org.safegyn.model.data.DoctorData;
import org.safegyn.model.data.DoctorSearchData;
import org.safegyn.model.error.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class DoctorController {

    @Autowired
    private DoctorDto doctorDto;

    @ApiOperation(value = "Search doctors by params")
    @RequestMapping(path = "/api/doctors", method = RequestMethod.GET)
    public DoctorSearchData searchDoctors(@RequestParam String city,
                                          @RequestParam(required = false) String gender,
                                          @RequestParam(required = false) Integer overallRating,
                                          @RequestParam(required = false) Integer professionalRating,
                                          @RequestParam(required = false) Integer objectiveRating,
                                          @RequestParam(required = false) Integer respectfulRating,
                                          @RequestParam(required = false) Integer anonymityRating,
                                          @RequestParam(required = false) Integer inclusivityRating) throws ApiException {
        return doctorDto.search(city, gender, overallRating, professionalRating, objectiveRating, respectfulRating, anonymityRating, inclusivityRating);
    }

    @ApiOperation(value = "Search doctors by ID")
    @RequestMapping(path = "/api/doctors/{doctorId}", method = RequestMethod.GET)
    public DoctorData getDoctor(@PathVariable Long doctorId) throws ApiException {
        return doctorDto.getCheck(doctorId);
    }

    @ApiOperation(value = "Get city list")
    @RequestMapping(path = "/api/cities", method = RequestMethod.GET)
    public List<String> getCities() {
        return doctorDto.getCities();
    }

}