package org.safegyn.service;

import org.safegyn.api.SubmissionApi;
import org.safegyn.db.entity.Submission;
import org.safegyn.model.error.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = ApiException.class)
public class SubmissionService {

    @Autowired
    private SubmissionApi submissionApi;

    public Submission getCheck(Long id) throws ApiException {
        return submissionApi.getCheck(id);
    }

    public List<Submission> getByDoctorId(Long doctorId) {
        return submissionApi.getByDoctorId(doctorId);
    }

}
