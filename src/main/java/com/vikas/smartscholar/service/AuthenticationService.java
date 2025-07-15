package com.vikas.smartscholar.service;

import com.vikas.smartscholar.dto.AuthRequest;
import com.vikas.smartscholar.dto.AuthResponse;
import com.vikas.smartscholar.model.Role;
import com.vikas.smartscholar.model.User;
import com.vikas.smartscholar.repository.UserRepository;
import com.vikas.smartscholar.security.JwtService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

@Autowired
public AuthenticationService(
        UserRepository userRepository,
        JwtService jwtService,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.passwordEncoder = passwordEncoder;
}

public AuthResponse register(AuthRequest request) {
    User user = new User();
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.USER);
    user.setRegisteredAt(LocalDate.now());
    user.setUsername(request.getEmail());
    
    userRepository.save(user);
    
    String jwtToken = jwtService.generateToken(user);
    AuthResponse response = new AuthResponse();
    response.setToken(jwtToken);
    return response;
}
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );
        UserDetails user = userRepository.findByEmail(request.getEmail())
            .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        AuthResponse response = new AuthResponse();
        response.setToken(jwtToken);
        return response;
    }

}