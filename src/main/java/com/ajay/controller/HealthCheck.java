package com.ajay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping("heatlh-check")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("App Running",HttpStatus.OK);
    }
}
