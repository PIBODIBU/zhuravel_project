package main.dao.impl;

import main.dao.UserRoleDAO;
import main.model.UserRole;
import org.hibernate.SessionFactory;

public class UserRoleDAOImpl extends BasicDAOImpl<UserRole> implements UserRoleDAO {
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
