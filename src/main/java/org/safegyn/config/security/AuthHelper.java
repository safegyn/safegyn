package org.safegyn.config.security;

import org.safegyn.db.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuthHelper {

    public static void createContext(HttpSession session) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
    }

    public static UsernamePasswordAuthenticationToken getAuthenticationToken(User user, List<UserRole> roles) {
        UserPrincipal principal = getPrincipal(user, roles);
        List<GrantedAuthority> authorityList = getAuthorities(principal.getUserRoles().stream().map(Enum::toString).collect(Collectors.toList()));
        return new UsernamePasswordAuthenticationToken(principal, null, authorityList);
    }

    public static UserPrincipal getPrincipal(User userEntity, List<UserRole> roles) {
        UserPrincipal principal = new UserPrincipal();
        principal.setUserId(userEntity.getUserId());
        principal.setUsername(userEntity.getUsername());
        principal.setUserRoles(roles);
        return principal;
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static void setAuthentication(Authentication token) {
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public static UserPrincipal getPrincipal() {
        Authentication token = getAuthentication();
        return token == null ? null : (UserPrincipal) getAuthentication().getPrincipal();
    }

    public static List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (String roleName : roles)
            authorityList.add(new SimpleGrantedAuthority(roleName));
        return authorityList;
    }

}

