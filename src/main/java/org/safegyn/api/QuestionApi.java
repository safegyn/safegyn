package org.safegyn.api;

import org.safegyn.db.dao.QuestionDao;
import org.safegyn.db.entity.Question;
import org.safegyn.model.error.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class QuestionApi extends AbstractApi {

    @Autowired
    private QuestionDao dao;

    public void add(Question question) throws ApiException {
        validateAddition(question);
        dao.persist(question);
    }

    private void validateAddition(Question question) throws ApiException {
        Question exists = dao.select("title", question.getTitle());
        checkNull(exists,
                ApiException.Type.USER_ERROR,
                "Adding question",
                "Question already exists with similar text",
                "Use a different text to describe the question");
    }

    public Question getCheck(Long id) throws ApiException {
        Question question = dao.select(id);
        checkNotNull(question,
                ApiException.Type.USER_ERROR,
                "Getting question by ID",
                "No question exists with ID:" + id,
                "Double check the ID used for lookup");

        return question;
    }

    public Question getCheck(String title) throws ApiException {
        Question question = dao.select("title", title);
        checkNotNull(question,
                ApiException.Type.USER_ERROR,
                "Getting question by title",
                "No question exists with title as:\"" + title + "\"",
                "Double check the title used for lookup");

        return question;
    }

    public List<Question> get(Question.Category category) {
        return dao.selectMultiple("category", category);
    }

    public void update(Long id, String title, Question.Category category) throws ApiException {
        Question question = getCheck(id);
        checkDuplicateTitle(title, id);

        if (Objects.nonNull(title)) question.setTitle(title);
        if (Objects.nonNull(category)) question.setCategory(category);
    }

    private void checkDuplicateTitle(String title, Long id) throws ApiException {
        Question exists = dao.select("title", title);
        if (Objects.nonNull(exists) && !exists.getId().equals(id))
            throw new ApiException(
                    ApiException.Type.USER_ERROR,
                    "Updating question title",
                    "Question already exists with title as:\"" + title + "\"",
                    "Use a unique question title");
    }
}


