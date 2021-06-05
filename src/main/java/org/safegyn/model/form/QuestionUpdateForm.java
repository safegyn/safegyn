package org.safegyn.model.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.safegyn.db.entity.Question;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionUpdateForm {

    @NotNull
    private Long id;

    private String title;

    private Question.Category category;

}
