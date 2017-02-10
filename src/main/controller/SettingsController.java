package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/settings")
public class SettingsController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView listSettings(HttpSession session,
                                     HttpServletResponse servletResponse) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("settings.jsp");
        modelAndView.addObject("title", "Settings");

        return modelAndView;
    }
}
