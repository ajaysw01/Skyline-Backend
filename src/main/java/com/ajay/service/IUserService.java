package com.ajay.service;

import com.ajay.exception.UserAlreadyExistsException;
import com.ajay.model.User;

import java.util.List;

public interface IUserService {

    User registerUsr(User user) throws UserAlreadyExistsException;
    List<User> getAllUsers();
    void deleteUser(String email);
    User getUser(String email);

}
