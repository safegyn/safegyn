package org.safegyn.util;

import org.safegyn.config.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static UserPrincipal getPrincipal() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
