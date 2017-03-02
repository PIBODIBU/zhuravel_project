package main.controller;

import main.dao.PassportFileDAO;
import main.dao.UserDAO;
import main.dao.UserDataDAO;
import main.dao.UserRoleDAO;
import main.helper.FileUploader;
import main.model.User;
import main.model.UserData;
import main.model.UserRole;
import main.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private UserDAO userDAO;
    private UserDataDAO userDataDAO;
    private UserRoleDAO userRoleDAO;
    private PassportFileDAO passportFileDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setUserDataDAO(UserDataDAO userDataDAO) {
        this.userDataDAO = userDataDAO;
    }

    @Autowired
    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    @Autowired
    public void setPassportFileDAO(PassportFileDAO passportFileDAO) {
        this.passportFileDAO = passportFileDAO;
    }

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