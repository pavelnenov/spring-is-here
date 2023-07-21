package com.pavel.spring.springishere.controllers;

import com.pavel.spring.springishere.config.JwtUtil;
import com.pavel.spring.springishere.dao.UserDao;
import com.pavel.spring.springishere.dto.AuthenticationRequest;
import com.pavel.spring.springishere.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Runnable r = () -> {
            while(true) {
                System.out.println("Hello from thread");
                String something = "something";
                try {
                    Thread.sleep(500);
                    System.out.println("I waited for " + something);
                    something = "something else";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(r);
        t.setName("My thread");
        t.start();

        UserDao user = userRepository.findByEmail(request.getEmail());
//        final UserDetails userDetails = User.builder()
//                .username(user.email())
//                .password(user.password())
//                .authorities("USER")
//                .build();

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            final String token = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(token);
        }
    }
}
