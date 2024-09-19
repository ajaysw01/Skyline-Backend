package com.ajay.service;

import com.ajay.exception.UserAlreadyExistsException;
import com.ajay.model.User;

import java.util.List;

/**
 * @author Simpson Alfred
 */

public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}