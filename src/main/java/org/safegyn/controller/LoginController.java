package org.safegyn.controller;

import io.swagger.annotations.ApiOperation;
import org.safegyn.api.UserApi;
import org.safegyn.config.security.AuthHelper;
import org.safegyn.config.security.UserRole;
import org.safegyn.db.entity.User;
import org.safegyn.model.data.LoginMessageData;
import org.safegyn.model.error.ApiException;
import org.safegyn.model.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
public class LoginController {

    private static final int SESSION_MAX_ACTIVE_SECONDS = 60 * 60;

    @Autowired
    private UserApi userApi;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private LoginMessageData data;

    @ApiOperation(value = "Log in a user using their username and password")
    @RequestMapping(path = "/api/admin/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView login(HttpServletRequest req, LoginForm loginForm) {
        User user = null;
        try {
            user = userApi.getCheckByUsername(loginForm.getUsername());
        } catch (ApiException ignored) {
        }

        boolean authenticated = (Objects.nonNull(user) && passwordEncoder.matches(loginForm.getPassword(), user.getPasswordDigest()));
        if (!authenticated) {
            data.setMessage("Invalid username or password");
            return new ModelAndView("redirect:/login");
        }

        List<UserRole> userRoles = Collections.singletonList(user.getUserRole());
        Authentication authentication = AuthHelper.getAuthenticationToken(user, userRoles);
        HttpSession session = req.getSession(true);
        session.setMaxInactiveInterval(SESSION_MAX_ACTIVE_SECONDS);
        AuthHelper.createContext(session);
        AuthHelper.setAuthentication(authentication);
        return new ModelAndView("redirect:/home");
    }

    @ApiOperation(value = "Log out a user")
    @RequestMapping(path = "/api/admin/logout", method = RequestMethod.POST)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
    }

}

