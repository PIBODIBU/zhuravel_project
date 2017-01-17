package main.controller;

import main.dao.UserDAO;
import main.dao.impl.UserDAOImpl;
import main.model.User;
import main.model.UserRole;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserListController {
    @RequestMapping("/")
    public ModelAndView userList() {
        UserDAO userDAO = new UserDAOImpl();
        ModelAndView modelAndView = new ModelAndView("/user_list.jsp");

        modelAndView.addObject("users", userDAO.getAll());

        for (User user : userDAO.getAll()) {
            System.out.println(user.getName() + " : " + user.getUserRoles().size());

            for (UserRole userRole : user.getUserRoles()) {
                System.out.println("\t\t-> " + userRole.getRole());
            }
        }

        return modelAndView;
    }
}
