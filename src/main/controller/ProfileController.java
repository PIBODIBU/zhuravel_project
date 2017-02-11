package main.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.UserDAO;
import main.dao.impl.UserDAOImpl;
import main.helper.Const;
import main.hibernate.serializer.OrderSerializer;
import main.hibernate.serializer.UserSerializer;
import main.model.Order;
import main.model.User;
import main.security.SecurityManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
        modelAndView.addObject("isMyPage", true);

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

    @RequestMapping(value = "/me/edit/", method = RequestMethod.GET)
    public ModelAndView editProfile(HttpSession httpSession,
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

        modelAndView.setViewName("profile_edit.jsp");
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @RequestMapping(value = "/me/edit/", method = RequestMethod.POST)
    public void editProfile(ServletRequest request,
                            HttpSession httpSession,
                            HttpServletResponse servletResponse,
                            @RequestParam("passportPhoto") List<MultipartFile> passportPhotos,
                            @ModelAttribute User user,
                            BindingResult bindingResult) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        SecurityManager securityManager = new SecurityManager(httpSession);
        UserDAO userDAO = new UserDAOImpl();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        if (!securityManager.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return;
        }

        // Check for errors
        if (bindingResult.hasErrors()) {
            servletResponse.sendRedirect("/user/me/edit/");
            return;
        }

        for (MultipartFile photo : passportPhotos) {
            if (photo != null && !photo.isEmpty()) {
                // Upload photo
                String realPathToUploads = request.getServletContext().getRealPath(Const.PASSPORT_SCAN_UPLOAD_PATH);
                if (!new File(realPathToUploads).exists()) {
                    new File(realPathToUploads).mkdir();
                }

                String filePath = realPathToUploads + photo.getOriginalFilename();
                File destFile = new File(filePath);
                photo.transferTo(destFile);

            }
        }

        userDAO.insertOrUpdate(user);

        servletResponse.sendRedirect("/user/me");
    }

    @RequestMapping("/{user_id}")
    public ModelAndView getUserPage(@PathVariable("user_id") Integer id,
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
        modelAndView.addObject("isMyPage", false);

        // Check if user is trying to get his profile
        if (Objects.equals(requestedUser.getId(), securityManager.getUser().getId())) {
            modelAndView.addObject("isMyPage", true);
        }

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
