package main.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.UserDAO;
import main.dao.impl.UserDAOImpl;
import main.hibernate.serializer.OrderSerializer;
import main.model.Order;
import main.model.User;
import main.security.SecurityFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@Scope("session")
@RequestMapping("/order")
public class OrderController {
    @RequestMapping("/my")
    public ModelAndView myOrders(HttpSession session,
                                 HttpServletResponse servletResponse) throws IOException {
        SecurityFilter securityFilter = new SecurityFilter(session);
        ModelAndView modelAndView = new ModelAndView();
        UserDAO userDAO = new UserDAOImpl();
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
                    gson.toJson(userDAO.get(securityFilter
                            .getUser()
                            .getId())
                            .getOrdersAsAgent()));

            modelAndView.setViewName("agent_order_list.jsp");
        } else if (securityFilter.has(SecurityFilter.ROLE_USER)) {
            modelAndView.addObject("orders",
                    gson.toJson(userDAO.get(securityFilter
                            .getUser()
                            .getId())
                            .getOrdersAsBuyer()));

            modelAndView.setViewName("user_order_list.jsp");
        } else {
            servletResponse.sendRedirect("/logout");
            return null;
        }

        return modelAndView;
    }

    @RequestMapping("/admin")
    public ModelAndView adminOrders(HttpSession session,
                                    HttpServletResponse servletResponse) throws IOException {
        SecurityFilter securityFilter = new SecurityFilter(session);

        if (!securityFilter.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityFilter.has(SecurityFilter.ROLE_ADMIN)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        ModelAndView modelAndView = new ModelAndView("user_order_list.jsp");

        return modelAndView;
    }

    @RequestMapping("/agent")
    public ModelAndView agentOrders(HttpSession session,
                                    HttpServletResponse servletResponse) throws IOException {
        SecurityFilter securityFilter = new SecurityFilter(session);

        if (!securityFilter.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityFilter.has(SecurityFilter.ROLE_AGENT)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        ModelAndView modelAndView = new ModelAndView("agent_order_list.jsp");

        return modelAndView;
    }
}
