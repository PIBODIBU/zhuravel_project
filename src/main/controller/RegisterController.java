package main.controller;

import main.dao.PassportFileDAO;
import main.dao.UserDAO;
import main.dao.UserDataDAO;
import main.dao.UserRoleDAO;
import main.dao.impl.PassportFileDAOImpl;
import main.dao.impl.UserDAOImpl;
import main.dao.impl.UserDataDAOImpl;
import main.dao.impl.UserRoleDAOImpl;
import main.helper.Const;
import main.helper.FileUploader;
import main.model.PassportFile;
import main.model.User;
import main.model.UserData;
import main.model.UserRole;
import main.security.SecurityManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void registerNewBuyer(HttpSession session,
                                 HttpServletRequest request,
                                 HttpServletResponse servletResponse,
                                 @RequestParam("passportPhoto") List<MultipartFile> multipartFiles,
                                 @ModelAttribute User user, BindingResult resultUser) throws IOException {
        if (resultUser.hasErrors()) {
            servletResponse.sendRedirect("/login");
            return;
        }

        UserDAO userDAO = new UserDAOImpl();
        UserDataDAO userDataDAO = new UserDataDAOImpl();
        UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
        PassportFileDAO passportFileDAO = new PassportFileDAOImpl();
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

        // Upload passport photos
        FileUploader.uploadFromMultipart(request.getServletContext(), user, multipartFiles);

        // Save user data to the session
        session.setAttribute(LoginController.ATTRIBUTE_USER, user);

        // Redirect
        servletResponse.sendRedirect("/order/active");
    }
}