package org.safegyn.model.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.safegyn.db.entity.Question;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionForm {

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 255, message = "Question title must be between 3 and 255 characters")
    private String title;

    @Column(nullable = false)
    private Question.Category category;

}
