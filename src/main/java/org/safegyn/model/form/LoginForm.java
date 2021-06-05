package org.safegyn.model.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginForm {

    @Email
    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

}
