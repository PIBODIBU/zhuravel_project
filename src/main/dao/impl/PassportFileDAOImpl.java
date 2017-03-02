package main.dao.impl;

import main.dao.PassportFileDAO;
import main.hibernate.HibernateUtil;
import main.model.Order;
import main.model.PassportFile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class PassportFileDAOImpl extends BasicDAOImpl<PassportFile> implements PassportFileDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PassportFile get(Integer id) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        PassportFile passportFile = ((PassportFile) session.get(PassportFile.class, id));
        session.refresh(passportFile);

        session.getTransaction().commit();

        session.close();

        return passportFile;
    }
}
