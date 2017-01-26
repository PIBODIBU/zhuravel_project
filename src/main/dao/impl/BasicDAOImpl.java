package main.dao.impl;

import main.dao.BasicDAO;
import main.hibernate.HibernateUtil;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicDAOImpl<T> implements BasicDAO<T> {
    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public String toJson(ArrayList<T> models) {
        return null;
    }

    @Override
    public String toJson(T model) {
        return null;
    }

    @Override
    public T get(Integer id) {
        return null;
    }

    @Override
    public Integer insert(T model) {
        Session session = HibernateUtil.getSession();

        Integer id;

        session.beginTransaction();
        id = ((Integer) session.save(model));
        session.getTransaction().commit();

        return id;
    }

    @Override
    public void insertOrUpdate(T model) {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        session.saveOrUpdate(model);
        session.getTransaction().commit();
    }

    @Override
    public void update(T model) {
    }

    @Override
    public void delete(T model) {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        session.delete(model);
        session.getTransaction().commit();
    }
}

