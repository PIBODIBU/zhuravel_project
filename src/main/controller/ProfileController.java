package main.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.UserDAO;
import main.dao.impl.UserDAOImpl;
import main.hibernate.serializer.OrderSerializer;
import main.hibernate.serializer.UserSerializer;
import main.model.Order;
import main.model.User;
import main.security.SecurityManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@Scope("session")
@RequestMapping("/user")
public class ProfileController {
    @RequestMapping("/me")
    public ModelAndView getMyPage(HttpSession httpSession,
                                  HttpServletResponse servletResponse) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        SecurityManager securityManager = new SecurityManager(httpSession);
        UserDAO userDAO = new UserDAOImpl();
        User user;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        if (!securityManager.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        user = userDAO.get(securityManager.getUser().getId());

        modelAndView.addObject("userModel", user);
        modelAndView.addObject("user", gson.toJson(user));

        if (securityManager.has(SecurityManager.ROLE_ADMIN)) {
        }

        if (securityManager.has(SecurityManager.ROLE_AGENT)) {
            modelAndView.setViewName("profile_agent.jsp");
        }

        if (securityManager.is(SecurityManager.ROLE_USER)) {
            modelAndView.addObject("orders", gson.toJson(user.getOrdersAsBuyer()));
            modelAndView.setViewName("profile_user.jsp");
        }

        return modelAndView;
    }

    @RequestMapping("/{id}")
    public ModelAndView getUserPage(@PathVariable("id") Integer id,
                                    HttpSession httpSession,
                                    HttpServletResponse servletResponse) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        SecurityManager securityManager = new SecurityManager(httpSession);
        UserDAO userDAO = new UserDAOImpl();
        User requestedUser = userDAO.get(id);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        if (!securityManager.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        // Check permission
        if (!securityManager.hasOneOf(
                SecurityManager.ROLE_AGENT,
                SecurityManager.ROLE_ADMIN)) {
            servletResponse.sendRedirect("/user/me");
            return null;
        }

        modelAndView.addObject("user", gson.toJson(requestedUser));
        modelAndView.addObject("userModel", requestedUser);

        // Switch pages
        if (requestedUser.isRole(SecurityManager.ROLE_USER)) {
            modelAndView.addObject("orders", gson.toJson(requestedUser.getOrdersAsBuyer()));
            modelAndView.setViewName("profile_user.jsp");
            return modelAndView;
        }
        if (requestedUser.hasRole(SecurityManager.ROLE_AGENT)) {
            modelAndView.setViewName("profile_agent.jsp");
            return modelAndView;
        }
        if (requestedUser.hasRole(SecurityManager.ROLE_ADMIN)) {
        }

        return null;
    }
}
