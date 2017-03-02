package main.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.OrderDAO;
import main.dao.UserDAO;
import main.dao.impl.OrderDAOImpl;
import main.dao.impl.UserDAOImpl;
import main.hibernate.serializer.OrderSerializer;
import main.model.Order;
import main.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@Scope("session")
@RequestMapping("/order")
public class OrderController {
    private UserDAO userDAO;
    private OrderDAO orderDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @RequestMapping(value = "/undefined", method = RequestMethod.GET)
    public ModelAndView undefinedOrders(HttpSession session,
                                        HttpServletResponse servletResponse) throws IOException {
        SecurityManager securityManager = new SecurityManager(session);
        ModelAndView modelAndView = new ModelAndView();
        UserDAO userDAO = new UserDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        modelAndView.addObject("title", "Undefined orders");

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

        if (securityManager.has(SecurityManager.ROLE_ADMIN)) {

        } else if (securityManager.has(SecurityManager.ROLE_AGENT)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getUndefinedOrders(
                            userDAO.get(securityManager
                                    .getUser()
                                    .getUserId()))));

            modelAndView.setViewName("order_list-agent.jsp");
        } else {
            servletResponse.sendRedirect("/logout");
            return null;
        }

        return modelAndView;
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ModelAndView activeOrders(HttpSession session,
                                     HttpServletResponse servletResponse) throws IOException {
        SecurityManager securityManager = new SecurityManager(session);
        ModelAndView modelAndView = new ModelAndView();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        modelAndView.addObject("title", "Active orders");

        if (!securityManager.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityManager.hasOneOf(
                SecurityManager.ROLE_AGENT,
                SecurityManager.ROLE_ADMIN,
                SecurityManager.ROLE_USER)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (securityManager.has(SecurityManager.ROLE_ADMIN)) {

        } else if (securityManager.has(SecurityManager.ROLE_AGENT)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getActiveOrdersAsAgent(
                            userDAO.get(securityManager
                                    .getUser()
                                    .getUserId()))));

            modelAndView.setViewName("order_list-agent.jsp");
        } else if (securityManager.has(SecurityManager.ROLE_USER)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getActiveOrdersAsBuyer(
                            userDAO.get(securityManager
                                    .getUser()
                                    .getUserId()))));

            modelAndView.setViewName("order_list-user.jsp");
        } else {
            servletResponse.sendRedirect("/logout");
            return null;
        }

        return modelAndView;
    }

    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public ModelAndView doneOrders(HttpSession session,
                                   HttpServletResponse servletResponse) throws IOException {
        SecurityManager securityManager = new SecurityManager(session);
        ModelAndView modelAndView = new ModelAndView();
        UserDAO userDAO = new UserDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        modelAndView.addObject("title", "Completed orders");

        if (!securityManager.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityManager.hasOneOf(
                SecurityManager.ROLE_AGENT,
                SecurityManager.ROLE_ADMIN,
                SecurityManager.ROLE_USER)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (securityManager.has(SecurityManager.ROLE_ADMIN)) {

        } else if (securityManager.has(SecurityManager.ROLE_AGENT)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getDoneOrdersAsAgent(
                            userDAO.get(securityManager
                                    .getUser()
                                    .getUserId()))));

            modelAndView.setViewName("order_list-agent.jsp");
        } else if (securityManager.has(SecurityManager.ROLE_USER)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getDoneOrdersAsBuyer(
                            userDAO.get(securityManager
                                    .getUser()
                                    .getUserId()))));

            modelAndView.setViewName("order_list-user.jsp");
        } else {
            servletResponse.sendRedirect("/logout");
            return null;
        }

        return modelAndView;
    }

    @RequestMapping(value = "/canceled", method = RequestMethod.GET)
    public ModelAndView closedOrders(HttpSession session,
                                     HttpServletResponse servletResponse) throws IOException {
        SecurityManager securityManager = new SecurityManager(session);
        ModelAndView modelAndView = new ModelAndView();
        UserDAO userDAO = new UserDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        modelAndView.addObject("title", "Canceled orders");

        if (!securityManager.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityManager.hasOneOf(
                SecurityManager.ROLE_AGENT,
                SecurityManager.ROLE_ADMIN,
                SecurityManager.ROLE_USER)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (securityManager.has(SecurityManager.ROLE_ADMIN)) {

        } else if (securityManager.has(SecurityManager.ROLE_AGENT)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getCanceledOrdersAsAgent(
                            userDAO.get(securityManager
                                    .getUser()
                                    .getUserId()))));

            modelAndView.setViewName("order_list-agent.jsp");
        } else if (securityManager.has(SecurityManager.ROLE_USER)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getCanceledOrdersAsBuyer(
                            userDAO.get(securityManager
                                    .getUser()
                                    .getUserId()))));

            modelAndView.setViewName("order_list-user.jsp");
        } else {
            servletResponse.sendRedirect("/logout");
            return null;
        }

        return modelAndView;
    }

    @RequestMapping(value = "/archived", method = RequestMethod.GET)
    public ModelAndView archivedOrders(HttpSession session,
                                       HttpServletResponse servletResponse) throws IOException {
        SecurityManager securityManager = new SecurityManager(session);
        ModelAndView modelAndView = new ModelAndView();
        UserDAO userDAO = new UserDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        modelAndView.addObject("title", "Archived orders");

        if (!securityManager.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityManager.hasOneOf(
                SecurityManager.ROLE_AGENT,
                SecurityManager.ROLE_ADMIN,
                SecurityManager.ROLE_USER)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (securityManager.has(SecurityManager.ROLE_ADMIN)) {

        } else if (securityManager.has(SecurityManager.ROLE_AGENT)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getArchivedOrdersAsAgent(
                            userDAO.get(securityManager
                                    .getUser()
                                    .getUserId()))));

            modelAndView.setViewName("order_list-agent.jsp");
        } else if (securityManager.has(SecurityManager.ROLE_USER)) {
            servletResponse.sendRedirect("/order/active");
        } else {
            servletResponse.sendRedirect("/logout");
            return null;
        }

        return modelAndView;
    }
}
