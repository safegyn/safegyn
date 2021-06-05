package org.safegyn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class SiteUiController extends AbstractUiController {

    @RequestMapping(value = "")
    public ModelAndView index(HttpServletRequest request) {
        return getModelAndView("index.html");
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (Objects.nonNull(session.getAttribute("SPRING_SECURITY_CONTEXT")))
            return new ModelAndView("home.html");
        return getModelAndView("login.html");
    }

}
