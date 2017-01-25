package main.controller;

import main.security.SecurityFilter;
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
        SecurityFilter securityFilter = new SecurityFilter(session);

        if (!securityFilter.isUserLogged()) {
            servletResponse.sendRedirect("/login");
        } else {
            servletResponse.sendRedirect("/order/active");
        }
    }
}
