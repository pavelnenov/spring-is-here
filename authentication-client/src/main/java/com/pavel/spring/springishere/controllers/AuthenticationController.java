package com.pavel.spring.springishere.controllers;

import com.pavel.spring.springishere.config.*;
import com.pavel.spring.springishere.dao.UserDao;
import com.pavel.spring.springishere.dto.*;
import com.pavel.spring.springishere.repository.UserRepository;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;


    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserRepository userDao,
            JwtUtil jwtUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userDao;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDao user = userRepository.findByEmail(request.getEmail());
        final UserDetails userDetails = User.builder()
                .username(user.email())
                .password(user.password())
                .authorities("USER")
                .build();

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            final String token = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(token);
        }
    }
}
