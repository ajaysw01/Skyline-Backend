package com.ajay.controller;

import com.ajay.exception.UserAlreadyExistsException;
import com.ajay.model.User;
import com.ajay.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(User user){
        try{
            userService.registerUsr(user);
            return ResponseEntity.ok("User registered successfully");
        }catch(UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
