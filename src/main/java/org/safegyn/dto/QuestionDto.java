package org.safegyn.dto;

import org.safegyn.db.entity.Question;
import org.safegyn.model.data.QuestionData;
import org.safegyn.model.error.ApiException;
import org.safegyn.model.form.QuestionForm;
import org.safegyn.model.form.QuestionUpdateForm;
import org.safegyn.service.QuestionService;
import org.safegyn.util.ConvertUtil;
import org.safegyn.util.NormalizeUtil;
import org.safegyn.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionDto {

    @Autowired
    private QuestionService questionService;

    public Question addQuestion(QuestionForm form) throws ApiException {
        NormalizeUtil.normalize(form);
        ValidationUtil.validate(form);
        return questionService.addQuestion(form);
    }

    public void addQuestion(List<QuestionForm> forms) throws ApiException {
        for (QuestionForm form : forms)
            addQuestion(form);
    }

    public QuestionData getQuestionById(Long id) throws ApiException {
        return ConvertUtil.convert(questionService.getCheck(id), QuestionData.class);
    }

    public List<QuestionData> getQuestionsByCategory(Question.Category category) throws ApiException {
        return ConvertUtil.convert(questionService.getByCategory(category), QuestionData.class);
    }

    public void updateQuestion(QuestionUpdateForm updateForm) throws ApiException {
        ValidationUtil.validate(updateForm);
        NormalizeUtil.normalize(updateForm);
        questionService.updateQuestion(updateForm);
    }

}
