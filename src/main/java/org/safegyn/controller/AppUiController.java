package org.safegyn.controller;

import org.safegyn.config.security.UserRole;
import org.safegyn.util.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class AppUiController extends AbstractUiController {

	@RequestMapping(value = "/user")
	public ModelAndView login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (Objects.isNull(session.getAttribute("SPRING_SECURITY_CONTEXT")))
			return new ModelAndView("login.html");
		if (SecurityUtil.getPrincipal().getUserRoles().contains(UserRole.ADMIN))
			return new ModelAndView("adminProfile.html");
		return new ModelAndView("redirect:/home");
	}

}