package main.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.SettingDAO;
import main.hibernate.serializer.ErrorStatusSerializer;
import main.model.ErrorStatus;
import main.model.Setting;
import main.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/settings")
public class SettingsAPIController {
    private SettingDAO settingDAO;
    private SecurityManager securityManager;

    @RequestMapping(value = "/set/", method = RequestMethod.POST)
    @ResponseBody
    public String setSetting(HttpSession session,
                             @RequestParam("name") String settingName,
                             @RequestParam("value") String settingValue) {
        ErrorStatus errorStatus = new ErrorStatus(false);
        Setting setting = new Setting();
        securityManager.setHttpSession(session);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ErrorStatus.class, new ErrorStatusSerializer())
                .create();

        if (!securityManager.has(SecurityManager.ROLE_AGENT)) {
            errorStatus.setError(true);
            errorStatus.setErrorMessage("You have no permission to change settings");
            return gson.toJson(errorStatus);
        }

        // Prepare setting model
        setting.setName(settingName);
        setting.setValue(settingValue);

        settingDAO.insertOrUpdate(setting);

        return gson.toJson(errorStatus);
    }

    @Autowired
    public void setSettingDAO(@Qualifier("settingDAO") SettingDAO settingDAO) {
        this.settingDAO = settingDAO;
    }

    @Autowired
    public void setSecurityManager(@Qualifier("securityManager") SecurityManager securityManager) {
        this.securityManager = securityManager;
    }
}
