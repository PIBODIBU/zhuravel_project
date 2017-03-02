package main.dao.impl;

import main.dao.UserDataDAO;
import main.hibernate.HibernateUtil;
import main.model.UserData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDataDAOImpl extends BasicDAOImpl<UserData> implements UserDataDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserData get(Integer id) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        UserData userData = ((UserData) session.get(UserData.class, id));
        session.refresh(userData);

        session.getTransaction().commit();

        session.close();

        return userData;
    }
}
