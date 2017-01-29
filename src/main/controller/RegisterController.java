package main.controller;

import main.dao.UserDAO;
import main.dao.UserDataDAO;
import main.dao.UserRoleDAO;
import main.dao.impl.UserDAOImpl;
import main.dao.impl.UserDataDAOImpl;
import main.dao.impl.UserRoleDAOImpl;
import main.model.User;
import main.model.UserData;
import main.model.UserRole;
import main.security.SecurityManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @RequestMapping("/")
    public void registerNewBuyer(HttpSession session,
                                 HttpServletResponse servletResponse,
                                 @ModelAttribute User user, BindingResult resultUser) throws IOException {
        if (resultUser.hasErrors()) {
            servletResponse.sendRedirect("/login");
            return;
        }

        UserDAO userDAO = new UserDAOImpl();
        UserDataDAO userDataDAO = new UserDataDAOImpl();
        UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
        UserData userData = user.getUserData();
        Integer userId;
        UserRole userRole = new UserRole();

        // Insert user model and get it back
        userId = userDAO.insert(user);
        user = userDAO.get(userId);

        // Bind user model to it's userdata model
        userData.setUser(user);

        // Insert user data model
        userDataDAO.insert(userData);

        // Refresh user model
        user = userDAO.get(userId);

        // Prepare user role model
        userRole.setUser(user);
        userRole.setRole(SecurityManager.ROLE_USER);

        // Insert user role model
        userRoleDAO.insert(userRole);

        // Refresh user model
        user = userDAO.get(userId);

        // Save user data to the session
        session.setAttribute(LoginController.ATTRIBUTE_USER, user);

        // Redirect
        servletResponse.sendRedirect("/order/active");
    }
}