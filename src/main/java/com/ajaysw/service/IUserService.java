package com.ajaysw.service;

import com.ajaysw.model.User;

import java.util.List;

/**
 * @author Ajay Wankhade
 */
public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
