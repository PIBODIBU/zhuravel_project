package main.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.dao.SettingDAO;
import main.hibernate.serializer.ServiceEmailSerializer;
import main.model.ServiceEmail;
import main.security.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/settings")
public class SettingsController {
    private SecurityManager securityManager;
    private SettingDAO settingDAO;

    @Autowired
    public void setSettingDAO(SettingDAO settingDAO) {
        this.settingDAO = settingDAO;
    }

    @Autowired
    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView listSettings(HttpSession session,
                                     HttpServletResponse servletResponse) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        securityManager.setHttpSession(session);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ServiceEmail.class, new ServiceEmailSerializer())
                .create();

        if (!securityManager.isUserLogged()) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        if (!securityManager.has(SecurityManager.ROLE_AGENT)) {
            servletResponse.sendRedirect("/login");
            return null;
        }

        modelAndView.setViewName("settings.jsp");
        modelAndView.addObject("title", "Settings");
        modelAndView.addObject("serviceEmails", gson.toJson(settingDAO.getServiceEmails()));

        return modelAndView;
    }


}