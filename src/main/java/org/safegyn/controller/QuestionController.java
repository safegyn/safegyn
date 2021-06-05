package org.safegyn.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.safegyn.db.entity.Question;
import org.safegyn.dto.QuestionDto;
import org.safegyn.model.data.QuestionData;
import org.safegyn.model.error.ApiException;
import org.safegyn.model.form.QuestionForm;
import org.safegyn.model.form.QuestionUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class QuestionController {

    @Autowired
    private QuestionDto questionDto;

    @ApiOperation(value = "Add new Questions")
    @RequestMapping(path = "/api/admin/questions", method = RequestMethod.POST)
    public void addQuestion(@RequestBody List<QuestionForm> forms) throws ApiException {
        questionDto.addQuestion(forms);
    }

    @ApiOperation(value = "Update a Question")
    @RequestMapping(path = "/api/admin/questions", method = RequestMethod.PATCH)
    public void updateQuestion(@RequestBody QuestionUpdateForm form) throws ApiException {
        questionDto.updateQuestion(form);
    }

    @ApiOperation(value = "Get a Question by ID")
    @RequestMapping(path = "/api/admin/questions/{id}", method = RequestMethod.GET)
    public QuestionData getQuestionById(@PathVariable Long id) throws ApiException {
        return questionDto.getQuestionById(id);
    }

    @ApiOperation(value = "Get Questions by category")
    @RequestMapping(path = "/api/admin/questions/category/{category}", method = RequestMethod.GET)
    public List<QuestionData> getQuestionByCategory(@PathVariable String category) throws ApiException {
        return questionDto.getQuestionsByCategory(Question.Category.valueOf(category));
    }

}