package main.controller;

import main.security.SecurityManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class RootPathController {

    @RequestMapping(method = RequestMethod.GET)
    public void dispatchRequest(HttpSession session,
                                HttpServletResponse servletResponse) throws IOException {
        SecurityManager securityManager = new SecurityManager(session);

        if (!securityManager.isUserLogged()) {
            servletResponse.sendRedirect("/login");
        } else {
            servletResponse.sendRedirect("/order/active");
        }
    }
}
