package org.safegyn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.safegyn.dto.ReviewDto;
import org.safegyn.model.data.DoctorReviewData;
import org.safegyn.model.error.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api
@RestController
public class ReviewingController {

    @Autowired
    private ReviewDto reviewDto;

    @ApiOperation(value = "Migration Endpoint: Add new Reviews using a city to list of users questions-answers map")
    @RequestMapping(path = "/api/admin/migration/reviews", method = RequestMethod.POST)
    public void addReviewByTitles(@RequestBody Map<String, List<Map<String, String>>> cityToQuestionAnswerMaps) throws ApiException {
        reviewDto.addReview(cityToQuestionAnswerMaps);
    }

    @ApiOperation(value = "Get reviews by doctor ID")
    @RequestMapping(path = "/api/reviews/doctor/{doctorId}", method = RequestMethod.GET)
    public Map<Long, DoctorReviewData> getReviews(@PathVariable Long doctorId) throws ApiException {
        return reviewDto.getReviewsByDoctorId(doctorId);
    }

}