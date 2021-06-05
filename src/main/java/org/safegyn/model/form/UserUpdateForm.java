package org.safegyn.model.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateForm {

    private String username;

    private String password;

    private String name;

}
