package main.dao;

import main.model.User;

public interface UserDAO extends BasicDAO<User> {
    User getByUsernameOrEmail(String usernameOrEmail, String password);
}
