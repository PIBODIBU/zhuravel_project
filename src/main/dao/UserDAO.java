package main.dao;

import main.model.User;

import java.util.List;

public interface UserDAO extends BasicDAO<User> {
    User getByUsernameOrEmail(String usernameOrEmail, String password);

    List<User> getBuyers();

    List<User> getAgents();
}
