package main.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.OrderDAO;
import main.dao.impl.OrderDAOImpl;
import main.hibernate.serializer.ErrorStatusSerializer;
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
}
