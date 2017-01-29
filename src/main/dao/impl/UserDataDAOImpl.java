package main.dao.impl;

import main.dao.UserDataDAO;
import main.hibernate.HibernateUtil;
import main.model.UserData;
import org.hibernate.Session;

public class UserDataDAOImpl extends BasicDAOImpl<UserData> implements UserDataDAO {
    @Override
    public UserData get(Integer id) {
        Session session = HibernateUtil.getSession();

        session.beginTransaction();
        UserData userData = ((UserData) session.get(UserData.class, id));
        session.refresh(userData);
        session.getTransaction().commit();

        return userData;
    }
}
