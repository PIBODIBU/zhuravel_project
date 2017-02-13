package main.dao.impl;

import com.google.gson.GsonBuilder;
import main.dao.UserDAO;
import main.hibernate.HibernateUtil;
import main.hibernate.serializer.UserSerializer;
import main.model.User;
import main.security.SecurityManager;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.type.StringType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDAOImpl extends BasicDAOImpl<User> implements UserDAO {
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<User> getAll() {
        Session session = HibernateUtil.getSession();
        ArrayList<User> users;

        session.beginTransaction();

        users = new ArrayList<User>(
                session.createCriteria(User.class)
                        .addOrder(Order.asc("name"))
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                        .list());

        session.getTransaction().commit();

        return users;
    }

    @Override
    public Integer insert(User model) {
        Session session = HibernateUtil.getSession();

        Integer id = -1;

        session.beginTransaction();
        id = ((Integer) session.save(model));
        session.getTransaction().commit();

        return id;
    }

    @SuppressWarnings("unchecked")
    @Override
    public User get(Integer id) {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        User user = ((User) session.get(User.class, id));
        session.refresh(user);
        session.getTransaction().commit();

        return user;
    }

    @Override
    public User getByUsernameOrEmail(String usernameOrEmail, String password) {
        Session session = HibernateUtil.getSession();
        User user = null;
        Criteria criteria;

        session.beginTransaction();

        // Try to get by username
        System.out.println("Trying to get user by username");
        criteria = session.createCriteria(User.class)
                .add(Expression.sql("BINARY username=?", usernameOrEmail, new StringType()))
                .add(Expression.sql("BINARY password=?", password, new StringType()));

        if (criteria.list().size() == 0) {
            // Try to get by email
            System.out.println("Username is not provided. Trying to get user by email");
            criteria = session.createCriteria(User.class)
                    .add(Expression.sql("BINARY email=?", usernameOrEmail, new StringType()))
                    .add(Expression.sql("BINARY password=?", password, new StringType()));
        }

        try {
            user = ((User) criteria.list().get(0));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        session.getTransaction().commit();

        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getBuyers() {
        Session session = HibernateUtil.getSession();
        SecurityManager securityManager = new SecurityManager();
        Iterator<User> iterator;
        List<User> buyers = new ArrayList<>();
        User user;

        session.beginTransaction();

        iterator = session
                .createCriteria(User.class)
                .addOrder(Order.asc("name"))
                .list()
                .iterator();

        while (iterator.hasNext()) {
            user = iterator.next();
            securityManager.setUser(user);

            if (securityManager.is(SecurityManager.ROLE_USER)) {
                session.refresh(user);
                buyers.add(user);
            }
        }

        session.getTransaction().commit();

        return buyers;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAgents() {
        Session session = HibernateUtil.getSession();
        SecurityManager securityManager = new SecurityManager();
        Iterator<User> iterator;
        List<User> agents = new ArrayList<>();
        User user;

        session.beginTransaction();

        iterator = session
                .createCriteria(User.class)
                .addOrder(Order.asc("name"))
                .list()
                .iterator();

        while (iterator.hasNext()) {
            user = iterator.next();
            securityManager.setUser(user);

            if (securityManager.is(SecurityManager.ROLE_AGENT)) {
                session.refresh(user);
                agents.add(user);
            }
        }

        session.getTransaction().commit();

        return agents;
    }

    @Override
    public String toJson(User user) {
        return new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .create()
                .toJson(user);
    }
}
