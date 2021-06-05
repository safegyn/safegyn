package org.safegyn.service;

import org.safegyn.api.QuestionApi;
import org.safegyn.db.entity.Question;
import org.safegyn.model.error.ApiException;
import org.safegyn.model.form.QuestionForm;
import org.safegyn.model.form.QuestionUpdateForm;
import org.safegyn.util.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = ApiException.class)
public class QuestionService {

    @Autowired
    private QuestionApi questionApi;

    public Question addQuestion(QuestionForm form) throws ApiException {
        Question question = ConvertUtil.convert(form, Question.class);
        questionApi.add(question);
        return question;
    }

    public Question getCheck(Long id) throws ApiException {
        return questionApi.getCheck(id);
    }

    public List<Question> getByCategory(Question.Category category) {
        return questionApi.get(category);
    }

    public void updateQuestion(QuestionUpdateForm updateForm) throws ApiException {
        questionApi.update(updateForm.getId(), updateForm.getTitle(), updateForm.getCategory());
    }
}
