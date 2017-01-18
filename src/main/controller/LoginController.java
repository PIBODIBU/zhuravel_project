package main.controller;

import main.dao.UserDAO;
import main.dao.impl.UserDAOImpl;
import main.model.Order;
import main.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {
    public static final String ATTRIBUTE_USER = "user";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpSession session,
                              HttpServletResponse servletResponse) throws IOException {
        if (session.getAttribute(ATTRIBUTE_USER) != null) {
            // If user is logged in - send redirect to default page
            servletResponse.sendRedirect("/order/my");
            return null;
        }

        ModelAndView model = new ModelAndView();

        model.setViewName("login.jsp");

        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpSession session,
                      HttpServletResponse servletResponse,
                      @RequestParam(value = "username") String username,
                      @RequestParam(value = "password") String password) throws IOException {
        if (session.getAttribute(ATTRIBUTE_USER) != null) {
            // If user is logged in - send redirect to default page
            servletResponse.sendRedirect("/order/my");
            return;
        }

        UserDAO userDAO = new UserDAOImpl();
        User user;

        // Get user
        user = userDAO.getByUsernameOrEmail(username, password);

        // Bad credentials
        if (user == null) {
            servletResponse.sendRedirect("/login");
            return;
        }

        // Save user data to the session
        session.setAttribute(ATTRIBUTE_USER, user);

        // Send redirect
        servletResponse.sendRedirect("/order/my");
    }

    @RequestMapping("/logout")
    public void logout(HttpSession session,
                       HttpServletResponse servletResponse) throws IOException {
        // Clear session
        session.invalidate();

        // Send redirect
        servletResponse.sendRedirect("/login");
    }
}