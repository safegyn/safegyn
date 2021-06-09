package org.safegyn.model.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginForm {

    @Email
    @NotNull
    @Size(min = 0, max = 50)
    private String username;

    @NotNull
    @Size(min = 6, max = 50)
    private String password;

}
