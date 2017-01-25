package main.dao.impl;

import main.dao.OrderDAO;
import main.hibernate.HibernateUtil;
import main.model.Order;
import main.model.User;
import org.hibernate.Session;

import java.util.Iterator;
import java.util.List;

public class OrderDAOImpl extends BasicDAOImpl<Order> implements OrderDAO {
    @SuppressWarnings("unchecked")
    @Override
    public Order get(Integer id) {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        Order order = ((Order) session.get(Order.class, id));
        session.refresh(order);
        session.getTransaction().commit();

        return order;
    }

    @Override
    public List<Order> getActiveOrdersAsAgent(User user) {
        List<Order> orders = user.getOrdersAsAgent();

        Iterator<Order> iterator = orders.iterator();

        while (iterator.hasNext()) {
            if (!this.isActive(iterator.next())) {
                iterator.remove();
            }
        }

        return orders;
    }

    @Override
    public List<Order> getActiveOrdersAsBuyer(User user) {
        List<Order> orders = user.getOrdersAsBuyer();

        Iterator<Order> iterator = orders.iterator();

        while (iterator.hasNext()) {
            if (!this.isActive(iterator.next())) {
                iterator.remove();
            }
        }

        return orders;
    }

    @Override
    public List<Order> getDoneOrdersAsAgent(User user) {
        List<Order> orders = user.getOrdersAsAgent();
        Iterator<Order> iterator = orders.iterator();

        while (iterator.hasNext()) {
            if (!this.isDone(iterator.next())) {
                iterator.remove();
            }
        }

        return orders;
    }

    @Override
    public List<Order> getDoneOrdersAsBuyer(User user) {
        List<Order> orders = user.getOrdersAsBuyer();
        Iterator<Order> iterator = orders.iterator();

        while (iterator.hasNext()) {
            if (!this.isDone(iterator.next())) {
                iterator.remove();
            }
        }

        return orders;
    }

    @Override
    public List<Order> getCanceledOrdersAsAgent(User user) {
        List<Order> orders = user.getOrdersAsAgent();

        Iterator<Order> iterator = orders.iterator();

        while (iterator.hasNext()) {
            if (!this.isCanceled(iterator.next())) {
                iterator.remove();
            }
        }

        return orders;
    }

    @Override
    public List<Order> getCanceledOrdersAsBuyer(User user) {
        List<Order> orders = user.getOrdersAsBuyer();

        Iterator<Order> iterator = orders.iterator();

        while (iterator.hasNext()) {
            if (!this.isCanceled(iterator.next())) {
                iterator.remove();
            }
        }

        return orders;
    }

    @Override
    public List<Order> getArchivedOrdersAsAgent(User user) {
        List<Order> orders = user.getOrdersAsAgent();

        Iterator<Order> iterator = orders.iterator();

        while (iterator.hasNext()) {
            if (!iterator.next().getArchived()) {
                iterator.remove();
            }
        }

        return orders;
    }

    @Override
    public List<Order> getArchivedOrdersAsBuyer(User user) {
        List<Order> orders = user.getOrdersAsBuyer();

        Iterator<Order> iterator = orders.iterator();

        while (iterator.hasNext()) {
            if (!iterator.next().getArchived()) {
                iterator.remove();
            }
        }

        return orders;
    }

    @Override
    public Boolean isActive(Order order) {
        if (order.getDone() || order.getArchived() || order.getCanceled()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean isDone(Order order) {
        if (!order.getDone() || order.getArchived() || order.getCanceled()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean isCanceled(Order order) {
        if (!order.getCanceled() || order.getArchived() || order.getDone()) {
            return false;
        } else {
            return true;
        }
    }
}
