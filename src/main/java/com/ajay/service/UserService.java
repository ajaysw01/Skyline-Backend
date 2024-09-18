package com.ajay.service;

import com.ajay.exception.UserAlreadyExistsException;
import com.ajay.model.Role;
import com.ajay.model.User;
import com.ajay.repository.RoleRepository;
import com.ajay.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUsr(User user) throws UserAlreadyExistsException {
        if(userRepository.existsByEmail(user.getEmail())){
            throw  new UserAlreadyExistsException(user.getEmail()+"already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        User theUser = getUser(email);
        if(theUser != null){
            userRepository.deleteByEmail(email);
        }

    }

    @Override
    public User getUser(String email) {
       return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not found"));
    }
}
