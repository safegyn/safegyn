package org.safegyn.api;

import org.safegyn.db.dao.SubmissionDao;
import org.safegyn.db.entity.Submission;
import org.safegyn.model.error.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionApi extends AbstractApi {

    @Autowired
    private SubmissionDao dao;

    public Submission add(Submission submission) throws ApiException {
        dao.persist(submission);
        return submission;
    }

    public Submission getCheck(Long id) throws ApiException {
        Submission submission = dao.select(id);

        checkNotNull(submission,
                ApiException.Type.USER_ERROR,
                "Fetching submission by ID",
                "Submission with ID:" + id + " does not exist",
                "Provide a valid submission ID");

        return submission;
    }

    public List<Submission> getByDoctorId(Long doctorId) {
        return dao.selectMultiple("doctorId", doctorId);
    }
}


