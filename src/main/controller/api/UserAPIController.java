package main.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.UserDAO;
import main.dao.impl.UserDAOImpl;
import main.hibernate.serializer.ErrorStatusSerializer;
import main.model.ErrorStatus;
import main.model.User;
import main.security.SecurityManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/users")
public class UserAPIController {
    @RequestMapping(value = "/{user_id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(HttpSession session,
                             @PathVariable("user_id") Integer userId) {
        SecurityManager securityManager = new SecurityManager(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        UserDAO userDAO = new UserDAOImpl();
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
}
