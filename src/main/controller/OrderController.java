package main.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.OrderDAO;
import main.dao.UserDAO;
import main.dao.impl.OrderDAOImpl;
import main.dao.impl.UserDAOImpl;
import main.hibernate.serializer.OrderSerializer;
import main.model.Order;
import main.security.SecurityFilter;
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
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public ModelAndView activeOrders(HttpSession session,
                                     HttpServletResponse servletResponse) throws IOException {
        SecurityFilter securityFilter = new SecurityFilter(session);
        ModelAndView modelAndView = new ModelAndView();
        UserDAO userDAO = new UserDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        if (!securityFilter.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityFilter.hasOneOf(
                SecurityFilter.ROLE_AGENT,
                SecurityFilter.ROLE_ADMIN,
                SecurityFilter.ROLE_USER)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (securityFilter.has(SecurityFilter.ROLE_ADMIN)) {

        } else if (securityFilter.has(SecurityFilter.ROLE_AGENT)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getActiveOrdersAsAgent(
                            userDAO.get(securityFilter
                                    .getUser()
                                    .getId()))));

            modelAndView.setViewName("agent_order_list.jsp");
        } else if (securityFilter.has(SecurityFilter.ROLE_USER)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getActiveOrdersAsBuyer(
                            userDAO.get(securityFilter
                                    .getUser()
                                    .getId()))));

            modelAndView.setViewName("user_order_list.jsp");
        } else {
            servletResponse.sendRedirect("/logout");
            return null;
        }

        return modelAndView;
    }

    @RequestMapping(value = "/done", method = RequestMethod.GET)
    public ModelAndView doneOrders(HttpSession session,
                                   HttpServletResponse servletResponse) throws IOException {
        SecurityFilter securityFilter = new SecurityFilter(session);
        ModelAndView modelAndView = new ModelAndView();
        UserDAO userDAO = new UserDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        if (!securityFilter.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityFilter.hasOneOf(
                SecurityFilter.ROLE_AGENT,
                SecurityFilter.ROLE_ADMIN,
                SecurityFilter.ROLE_USER)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (securityFilter.has(SecurityFilter.ROLE_ADMIN)) {

        } else if (securityFilter.has(SecurityFilter.ROLE_AGENT)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getDoneOrdersAsAgent(
                            userDAO.get(securityFilter
                                    .getUser()
                                    .getId()))));

            modelAndView.setViewName("agent_order_list.jsp");
        } else if (securityFilter.has(SecurityFilter.ROLE_USER)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getDoneOrdersAsBuyer(
                            userDAO.get(securityFilter
                                    .getUser()
                                    .getId()))));

            modelAndView.setViewName("user_order_list.jsp");
        } else {
            servletResponse.sendRedirect("/logout");
            return null;
        }

        return modelAndView;
    }

    @RequestMapping(value = "/canceled", method = RequestMethod.GET)
    public ModelAndView closedOrders(HttpSession session,
                                     HttpServletResponse servletResponse) throws IOException {
        SecurityFilter securityFilter = new SecurityFilter(session);
        ModelAndView modelAndView = new ModelAndView();
        UserDAO userDAO = new UserDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        if (!securityFilter.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityFilter.hasOneOf(
                SecurityFilter.ROLE_AGENT,
                SecurityFilter.ROLE_ADMIN,
                SecurityFilter.ROLE_USER)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (securityFilter.has(SecurityFilter.ROLE_ADMIN)) {

        } else if (securityFilter.has(SecurityFilter.ROLE_AGENT)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getCanceledOrdersAsAgent(
                            userDAO.get(securityFilter
                                    .getUser()
                                    .getId()))));

            modelAndView.setViewName("agent_order_list.jsp");
        } else if (securityFilter.has(SecurityFilter.ROLE_USER)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getCanceledOrdersAsBuyer(
                            userDAO.get(securityFilter
                                    .getUser()
                                    .getId()))));

            modelAndView.setViewName("user_order_list.jsp");
        } else {
            servletResponse.sendRedirect("/logout");
            return null;
        }

        return modelAndView;
    }

    @RequestMapping(value = "/archived", method = RequestMethod.GET)
    public ModelAndView archivedOrders(HttpSession session,
                                       HttpServletResponse servletResponse) throws IOException {
        SecurityFilter securityFilter = new SecurityFilter(session);
        ModelAndView modelAndView = new ModelAndView();
        UserDAO userDAO = new UserDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .create();

        if (!securityFilter.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityFilter.hasOneOf(
                SecurityFilter.ROLE_AGENT,
                SecurityFilter.ROLE_ADMIN,
                SecurityFilter.ROLE_USER)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (securityFilter.has(SecurityFilter.ROLE_ADMIN)) {

        } else if (securityFilter.has(SecurityFilter.ROLE_AGENT)) {
            modelAndView.addObject("orders",
                    gson.toJson(orderDAO.getArchivedOrdersAsAgent(
                            userDAO.get(securityFilter
                                    .getUser()
                                    .getId()))));

            modelAndView.setViewName("agent_order_list.jsp");
        } else if (securityFilter.has(SecurityFilter.ROLE_USER)) {
            servletResponse.sendRedirect("/order/active");
        } else {
            servletResponse.sendRedirect("/logout");
            return null;
        }

        return modelAndView;
    }
}