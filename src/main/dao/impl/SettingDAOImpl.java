package main.dao.impl;

import main.dao.SettingDAO;
import main.hibernate.HibernateUtil;
import main.model.Setting;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.type.StringType;

public class SettingDAOImpl extends BasicDAOImpl<Setting> implements SettingDAO {
    private static final String SETTING_SERVICE_EMAILS = "service_emails";

    @Override
    public Setting getByName(String name) {
        Session session = HibernateUtil.getSession();
        Criteria criteria;
        Setting setting = null;

        session.beginTransaction();

        criteria = session.createCriteria(Setting.class)
                .add(Expression.sql("BINARY name=?", name, new StringType()));

        try {
            setting = ((Setting) criteria.list().get(0));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        session.getTransaction().commit();

        return setting;
    }

    @Override
    public String[] getServiceEmails() {
        String[] emails;
        Setting setting;

        // Get setting model
        setting = getByName(SETTING_SERVICE_EMAILS);
        // Parse string with emails
        emails = setting.getValue().split(",");

        return emails;
    }
}
