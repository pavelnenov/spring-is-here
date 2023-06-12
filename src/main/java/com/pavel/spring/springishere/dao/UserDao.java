package com.pavel.spring.springishere.dao;

import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public class UserDao {

    private static final List<UserDetails> users = List.of(
            new User("pavel", "password", Collections.singleton(new SimpleGrantedAuthority("USER"))),
            new User("admin", "password", Collections.singleton(new SimpleGrantedAuthority("ADMIN")))
    );

    public UserDetails findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getUsername().equals(email))
                .findFirst()
                .orElse(null);
    }
}
