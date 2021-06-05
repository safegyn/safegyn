package org.safegyn.config.security;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UserPrincipal {

    @NotNull
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    private List<UserRole> userRoles = new ArrayList<>();

}
