package org.safegyn.model.form;

import org.safegyn.config.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserForm {

    @NotNull(message = "Username cannot be empty")
    private String username;

    @NotNull(message = "Password cannot be empty")
    private String password;

    private String name;

    @NotNull(message = "User Type cannot be empty")
    private UserRole userRole = UserRole.ADMIN;

}
