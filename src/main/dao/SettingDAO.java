package main.dao;

import main.model.Setting;

public interface SettingDAO extends BasicDAO<Setting> {
    String[] getServiceEmails();

    Setting getByName(String name);
}
