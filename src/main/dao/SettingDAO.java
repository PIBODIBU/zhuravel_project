package main.dao;

import main.model.ServiceEmail;
import main.model.Setting;

import java.util.List;

public interface SettingDAO extends BasicDAO<Setting> {
    String[] getServiceEmails();

    List<ServiceEmail> getServiceEmailModels();

    Setting getByName(String name);
}
