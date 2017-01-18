package main.controller;

import main.security.SecurityFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/service")
public class ServiceModeController {
    @RequestMapping("/user/dump")
    public ModelAndView dumpUser(HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView("service.jsp");
        SecurityFilter securityFilter = new SecurityFilter(httpSession);

        modelAndView.addObject("data", securityFilter.getUser().getUserData().toString());

        return modelAndView;
    }
}
