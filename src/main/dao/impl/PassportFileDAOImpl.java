package main.dao.impl;

import main.dao.PassportFileDAO;
import main.hibernate.HibernateUtil;
import main.model.Order;
import main.model.PassportFile;
import org.hibernate.Session;

public class PassportFileDAOImpl extends BasicDAOImpl<PassportFile> implements PassportFileDAO {
    @Override
    public PassportFile get(Integer id) {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        PassportFile passportFile = ((PassportFile) session.get(PassportFile.class, id));
        session.refresh(passportFile);
        session.getTransaction().commit();

        return passportFile;
    }
}
