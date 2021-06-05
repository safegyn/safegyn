package org.safegyn.controller;

import org.safegyn.config.ApplicationProperties;
import org.safegyn.config.security.AuthHelper;
import org.safegyn.config.security.UserPrincipal;
import org.safegyn.model.data.LoginMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public abstract class AbstractUiController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private LoginMessageData data;

    protected ModelAndView getModelAndView(String page) {
        UserPrincipal principal = AuthHelper.getPrincipal();
        data.setUsername(principal == null ? "" : principal.getUsername());
        ModelAndView mav = new ModelAndView(page);
        mav.addObject("baseUrl", applicationProperties.getBaseUrl());
        mav.addObject("data", data);
        return mav;
    }

}
