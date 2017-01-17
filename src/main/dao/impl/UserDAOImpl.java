package main.dao.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.UserDAO;
import main.hibernate.HibernateUtil;
import main.hibernate.serializer.UserSerializer;
import main.model.User;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;

import java.util.ArrayList;

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
    public String toJson(User user) {
        return new GsonBuilder()
                .registerTypeAdapter(User.class, new UserSerializer())
                .create()
                .toJson(user);
    }
}
