package main.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.UserDAO;
import main.dao.UserDataDAO;
import main.dao.UserRoleDAO;
import main.hibernate.serializer.ErrorStatusSerializer;
import main.hibernate.serializer.UserSerializer;
import main.model.ErrorStatus;
import main.model.User;
import main.model.UserData;
import main.model.UserRole;
import main.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/users")
public class UserAPIController {
    private UserDAO userDAO;
    private UserRoleDAO userRoleDAO;
    private UserDataDAO userDataDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }

    @Autowired
    public void setUserDataDAO(UserDataDAO userDataDAO) {
        this.userDataDAO = userDataDAO;
    }

    @RequestMapping(value = "/{user_id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(HttpSession session,
                             @PathVariable("user_id") Integer userId) {
        SecurityManager securityManager = new SecurityManager(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        User user;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ErrorStatus.class, new ErrorStatusSerializer())
                .create();

        if (!securityManager.isUserLogged()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You are not logged in");
            return gson.toJson(errorStatus);
        }

        if (!securityManager.has(SecurityManager.ROLE_AGENT)) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You don't have permissions to delete this user");
            return gson.toJson(errorStatus);
        }

        user = userDAO.get(userId);

        if (user == null) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Bad user id");
            return gson.toJson(errorStatus);
        }

        userDAO.delete(user);

        return gson.toJson(errorStatus);
    }

    @RequestMapping(value = "/agents/add", method = RequestMethod.POST)
    @ResponseBody
    public String addAgent(HttpSession session,
                           @RequestParam("data") String jsonData) {
        SecurityManager securityManager = new SecurityManager(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        User user;
        UserData userData;
        UserRole userRole = new UserRole();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .create();

        if (!securityManager.isUserLogged()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You are not logged in");
            return gson.toJson(errorStatus);
        }

        if (!securityManager.is(SecurityManager.ROLE_AGENT)) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You have no permissions to add agent");
            return gson.toJson(errorStatus);
        }

        // Create Java model
        user = gson.fromJson(jsonData, User.class);

        // Get user's data. We can't insert it, cause user doesn't have id yet
        userData = user.getUserData();

        // Insert user model into db
        Integer id = userDAO.insert(user);
        user = userDAO.get(id);

        // Use inserted model (id is not null anymore) and insert it
        if (userData != null) {
            userData.setUser(user);
            userDataDAO.insert(userData);
        }

        // Create and insert role
        userRole.setUser(user);
        userRole.setRole(SecurityManager.ROLE_AGENT);
        userRoleDAO.insert(userRole);

        return gson.toJson(user);
    }
}
