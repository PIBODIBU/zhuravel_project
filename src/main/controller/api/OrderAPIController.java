package main.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.OrderDAO;
import main.dao.UserDAO;
import main.dao.impl.OrderDAOImpl;
import main.dao.impl.UserDAOImpl;
import main.hibernate.serializer.ErrorStatusSerializer;
import main.hibernate.serializer.OrderSerializer;
import main.mail.MailManager;
import main.model.ErrorStatus;
import main.model.Order;
import main.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/order")
public class OrderAPIController {
   /* private MailManager mailManager;

    @Autowired
    public void setMailManager(@Qualifier("mailManager") MailManager mailManager) {
        this.mailManager = mailManager;
    }*/

    @RequestMapping(value = "/archive", method = RequestMethod.POST)
    @ResponseBody
    public String archiveOrder(HttpSession session,
                               @RequestParam(value = "order_id") Integer orderId) {
        SecurityManager securityManager = new SecurityManager(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        OrderDAO orderDAO = new OrderDAOImpl();
        Order order;
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

        if (!SecurityManager.Orders.ownsAsAgent(order, securityManager.getUser())) {
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
        SecurityManager securityManager = new SecurityManager(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        OrderDAO orderDAO = new OrderDAOImpl();
        Order order;
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

        if (!SecurityManager.Orders.ownsAsAgent(order, securityManager.getUser())) {
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

        new MailManager().notifyUserAboutCompletedOrder(order.getBuyer(), order);

        return gson.toJson(errorStatus);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    @ResponseBody
    public String cancelOrder(HttpSession session,
                              @RequestParam(value = "order_id") Integer orderId) {
        SecurityManager securityManager = new SecurityManager(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        OrderDAO orderDAO = new OrderDAOImpl();
        Order order;
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
            errorStatus.setErrorMessage("You don't have permissions to cancel this order");
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

        if (!SecurityManager.Orders.ownsAsAgent(order, securityManager.getUser())) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("This is not your order");
            return gson.toJson(errorStatus);
        }

        if (order.getCanceled()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Order is already canceled");
            return gson.toJson(errorStatus);
        }

        order.setCanceled(true);
        orderDAO.insertOrUpdate(order);

        return gson.toJson(errorStatus);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public String newOrder(HttpSession session,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "comment") String comment) {
        SecurityManager securityManager = new SecurityManager(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        OrderDAO orderDAO = new OrderDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        Order order = new Order();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .registerTypeAdapter(ErrorStatus.class, new ErrorStatusSerializer())
                .create();

        if (!securityManager.isUserLogged()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You are not logged in");
            return gson.toJson(errorStatus);
        }

        if (!securityManager.has(SecurityManager.ROLE_USER)) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You don't have permissions to create new order");
            return gson.toJson(errorStatus);
        }

        order.setBuyer(userDAO.get(securityManager.getUser().getUserId()));
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

    @RequestMapping(value = "/become", method = RequestMethod.POST)
    @ResponseBody
    public String becomeAgentOfOrder(HttpSession session,
                                     @RequestParam(value = "order_id") Integer orderId) {
        SecurityManager securityManager = new SecurityManager(session);
        ErrorStatus errorStatus = new ErrorStatus(false);
        OrderDAO orderDAO = new OrderDAOImpl();
        UserDAO userDAO = new UserDAOImpl();
        Order order;
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
            errorStatus.setErrorMessage("You don't have permissions to get this order");
            return gson.toJson(errorStatus);
        }

        order = orderDAO.get(orderId);

        if (order == null) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Order cannot be found");
            return gson.toJson(errorStatus);
        }

        if (!order.isUndefined()) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("Order's agent is already defined");
            return gson.toJson(errorStatus);
        }

        order.setAgent(userDAO.get(securityManager.getUser().getUserId()));
        orderDAO.insertOrUpdate(order);

        return gson.toJson(errorStatus);
    }
}
