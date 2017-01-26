package main.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.OrderDAO;
import main.dao.UserDAO;
import main.dao.impl.OrderDAOImpl;
import main.dao.impl.UserDAOImpl;
import main.hibernate.serializer.ErrorStatusSerializer;
import main.hibernate.serializer.OrderSerializer;
import main.model.ErrorStatus;
import main.model.Order;
import main.security.SecurityFilter;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/order")
public class OrderAPIController {
    @RequestMapping(value = "/archive", method = RequestMethod.POST)
    @ResponseBody
    public String archiveOrder(HttpSession session,
                               @RequestParam(value = "order_id") Integer orderId) {
        SecurityFilter securityFilter = new SecurityFilter(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        OrderDAO orderDAO = new OrderDAOImpl();
        Order order;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ErrorStatus.class, new ErrorStatusSerializer())
                .create();

        if (!securityFilter.isUserLogged()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You are not logged in");
            return gson.toJson(errorStatus);
        }

        if (!securityFilter.has(SecurityFilter.ROLE_AGENT)) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You don't have permissions to archive this order");
            return gson.toJson(errorStatus);
        }

        order = orderDAO.get(orderId);

        if (order == null) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Order cannot be found");
            return gson.toJson(errorStatus);
        }

        if (order.isUndefined()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Order's agent is undefined");
            return gson.toJson(errorStatus);
        }

        if (!SecurityFilter.Orders.ownsAsAgent(order, securityFilter.getUser())) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("This is not your order");
            return gson.toJson(errorStatus);
        }

        if (order.getArchived()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Order is already archived");
            return gson.toJson(errorStatus);
        }

        order.setArchived(true);
        orderDAO.insertOrUpdate(order);

        return gson.toJson(errorStatus);
    }

    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    @ResponseBody
    public String completeOrder(HttpSession session,
                                @RequestParam(value = "order_id") Integer orderId,
                                @RequestParam(value = "comment") String comment) {
        SecurityFilter securityFilter = new SecurityFilter(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        OrderDAO orderDAO = new OrderDAOImpl();
        Order order;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ErrorStatus.class, new ErrorStatusSerializer())
                .create();

        if (!securityFilter.isUserLogged()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You are not logged in");
            return gson.toJson(errorStatus);
        }

        if (!securityFilter.has(SecurityFilter.ROLE_AGENT)) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You don't have permissions to complete this order");
            return gson.toJson(errorStatus);
        }

        order = orderDAO.get(orderId);

        if (order == null) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Order cannot be found");
            return gson.toJson(errorStatus);
        }

        if (order.isUndefined()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Order's agent is undefined");
            return gson.toJson(errorStatus);
        }

        if (!SecurityFilter.Orders.ownsAsAgent(order, securityFilter.getUser())) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("This is not your order");
            return gson.toJson(errorStatus);
        }

        if (order.getDone()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Order is already completed");
            return gson.toJson(errorStatus);
        }

        order.setDone(true);
        order.setSoldComment(comment);
        orderDAO.insertOrUpdate(order);

        return gson.toJson(errorStatus);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public String newOrder(HttpSession session,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "comment") String comment) {
        SecurityFilter securityFilter = new SecurityFilter(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        OrderDAO orderDAO = new OrderDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        Order order = new Order();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .registerTypeAdapter(ErrorStatus.class, new ErrorStatusSerializer())
                .create();

        if (!securityFilter.isUserLogged()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You are not logged in");
            return gson.toJson(errorStatus);
        }

        if (!securityFilter.has(SecurityFilter.ROLE_USER)) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You don't have permissions to create new order");
            return gson.toJson(errorStatus);
        }

        order.setBuyer(userDAO.get(securityFilter.getUser().getId()));
        order.setBuyingItemName(name);
        order.setBuyingComment(comment);
        order.setDone(false);
        order.setCanceled(false);
        order.setArchived(false);

        Integer insertedId = orderDAO.insert(order);
        order = orderDAO.get(insertedId);

        if (order == null) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Server error occurred");
            return gson.toJson(errorStatus);
        }

        return gson.toJson(order);
    }
}
