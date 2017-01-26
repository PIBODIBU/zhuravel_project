package main.dao;

import main.model.Order;
import main.model.User;

import java.util.List;

public interface OrderDAO extends BasicDAO<Order> {
    List<Order> getUndefinedOrders(User user);

    List<Order> getActiveOrdersAsAgent(User user);

    List<Order> getActiveOrdersAsBuyer(User user);

    List<Order> getDoneOrdersAsAgent(User user);

    List<Order> getDoneOrdersAsBuyer(User user);

    List<Order> getCanceledOrdersAsAgent(User user);

    List<Order> getCanceledOrdersAsBuyer(User user);

    List<Order> getArchivedOrdersAsAgent(User user);

    List<Order> getArchivedOrdersAsBuyer(User user);

    Boolean isActive(Order order);

    Boolean isDone(Order order);

    Boolean isCanceled(Order order);
}
