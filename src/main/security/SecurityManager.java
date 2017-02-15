package main.security;

import main.controller.LoginController;
import main.model.Order;
import main.model.PassportFile;
import main.model.User;
import main.model.UserRole;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

public class SecurityManager {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_AGENT = "ROLE_AGENT";
    public static final String ROLE_USER = "ROLE_USER";

    private HttpSession httpSession;
    private User user;
    private LinkedList<String> authorizedRoles;

    public SecurityManager() {
        this.authorizedRoles = new LinkedList<>();
    }

    public SecurityManager(HttpSession httpSession) {
        this.httpSession = httpSession;
        this.user = ((User) this.httpSession.getAttribute(LoginController.ATTRIBUTE_USER));
        this.authorizedRoles = new LinkedList<>();
    }

    public void addAuthorizedRole(String role) {
        this.authorizedRoles.add(role);
    }

    public void dropAuthorizedRoles() {
        this.authorizedRoles.clear();
    }

    public Boolean isUserLogged() {
        try {
            if (getHttpSession() == null) {
                System.out.println("Http session is null");
                return false;
            }

            if (((User) getHttpSession().getAttribute(LoginController.ATTRIBUTE_USER)) == null) {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    public Boolean checkAuthorizedRoles() {
        if (getUser() == null) {
            System.out.println("User is null");
            return false;
        }

        Boolean passed = true;
        LinkedList<String> userRoles = getUser()
                .getUserRoles()
                .stream()
                .map(UserRole::getRole)
                .collect(Collectors.toCollection(LinkedList::new));

        for (String role : this.authorizedRoles) {
            if (!userRoles.contains(role)) {
                passed = false;
            }
        }

        return passed;
    }

    public Boolean hasAll(String... roles) {
        Boolean passed = true;
        LinkedList<String> userRoles = getUser()
                .getUserRoles()
                .stream()
                .map(UserRole::getRole)
                .collect(Collectors.toCollection(LinkedList::new));

        for (String role : roles) {
            if (!userRoles.contains(role)) {
                passed = false;
                break;
            }
        }

        return passed;
    }

    public Boolean hasOneOf(String... roles) {
        Boolean passed = false;
        LinkedList<String> userRoles = getUser()
                .getUserRoles()
                .stream()
                .map(UserRole::getRole)
                .collect(Collectors.toCollection(LinkedList::new));

        for (String role : roles) {
            if (userRoles.contains(role)) {
                passed = true;
            }
        }

        return passed;
    }

    public Boolean has(String role) {
        Boolean passed = true;
        LinkedList<String> userRoles = getUser()
                .getUserRoles()
                .stream()
                .map(UserRole::getRole)
                .collect(Collectors.toCollection(LinkedList::new));

        if (!userRoles.contains(role)) {
            passed = false;
        }

        return passed;
    }

    public Boolean is(String role) {
        Boolean passed = true;
        LinkedList<String> userRoles = getUser()
                .getUserRoles()
                .stream()
                .map(UserRole::getRole)
                .collect(Collectors.toCollection(LinkedList::new));

        if (userRoles.size() > 1) {
            passed = false;
        }

        if (!userRoles.contains(role)) {
            passed = false;
        }

        return passed;
    }

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static class Orders {
        public static Boolean ownsAsAgent(Order order, User user) {
            return Objects.equals(order.getAgent().getUserId(), user.getUserId());
        }
    }

    public static class PassportFiles {
        public static Boolean owns(PassportFile passportFile, User user) {
            Boolean owns = false;
            Integer passportFileId = passportFile.getId();

            for (PassportFile iterator : user.getUserData().getPassportFiles()) {
                if (Objects.equals(passportFileId, iterator.getId())) {
                    owns = true;
                    break;
                }
            }

            return owns;
        }
    }
}