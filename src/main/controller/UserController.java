package main.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.UserDAO;
import main.dao.impl.UserDAOImpl;
import main.hibernate.serializer.UserSerializer;
import main.model.User;
import main.security.SecurityManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/users")
public class UserController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView allUsers(HttpSession session,
                                 HttpServletResponse servletResponse) throws IOException {
        SecurityManager securityManager = new SecurityManager(session);
        ModelAndView modelAndView = new ModelAndView("user_list.jsp");
        UserDAO userDAO = new UserDAOImpl();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .create();

        if (!securityManager.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityManager.hasOneOf(
                SecurityManager.ROLE_AGENT,
                SecurityManager.ROLE_ADMIN)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        modelAndView.addObject("users", gson.toJson(userDAO.getBuyers()));
        modelAndView.addObject("agents", gson.toJson(userDAO.getAgents()));

        return modelAndView;
    }
}
