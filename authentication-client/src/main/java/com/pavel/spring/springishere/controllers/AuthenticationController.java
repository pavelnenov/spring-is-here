package com.pavel.spring.springishere.controllers;

import com.pavel.spring.springishere.config.*;
import com.pavel.spring.springishere.dao.*;
import com.pavel.spring.springishere.dto.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;

    private final JwtUtil jwtUtil;


    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserDao userDao,
            JwtUtil jwtUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        final UserDetails user = userDao.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            final String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(token);
        }
    }
}
