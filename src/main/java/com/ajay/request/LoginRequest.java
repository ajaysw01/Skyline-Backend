package com.ajay.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;



/**
 * @author Ajay Wankhade
 */
@Data
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
