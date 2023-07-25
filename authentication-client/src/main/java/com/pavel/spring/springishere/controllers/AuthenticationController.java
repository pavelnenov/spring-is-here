package com.pavel.spring.springishere.controllers;

import com.pavel.spring.springishere.dao.UserDao;
import com.pavel.spring.springishere.dto.AuthenticationRequest;
import com.pavel.spring.springishere.dto.RegistrationRequest;
import com.pavel.spring.springishere.jwt.JwtUtil;
import com.pavel.spring.springishere.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;


    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserRepository userDao
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userDao;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            final String userName = authentication.getName();
            final String token = JwtUtil.generateToken(userName);
            return ResponseEntity.ok(token);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        UserDao user = userRepository.findByEmail(request.email()).orElse(null);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            user = new UserDao(request.firstName(), request.lastName(), request.email(), request.password());
            userRepository.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }
}
