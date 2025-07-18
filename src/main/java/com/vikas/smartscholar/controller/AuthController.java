package com.vikas.smartscholar.controller;

import com.vikas.smartscholar.dto.AuthRequest;
import com.vikas.smartscholar.dto.AuthResponse;
import com.vikas.smartscholar.service.AuthenticationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;


    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
@PostMapping("/register")
public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
    return ResponseEntity.ok(authenticationService.register(request));
}

// {
//     "email": "rohan21@gmail.com",
//     "password": "r123"
// }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    // {
//     "email": "rohan21@gmail.com",
//     "password": "r123"
// }

}