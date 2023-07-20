package com.pavel.spring.springishere.repository;

import com.pavel.spring.springishere.dao.UserDao;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

//    private static final List<UserDetails> users = List.of(
//            new User("pavel", "password", Collections.singleton(new SimpleGrantedAuthority("USER"))),
//            new User("admin", "password", Collections.singleton(new SimpleGrantedAuthority("ADMIN")))
//    );

    UserDao createUser(UserDao user);

    UserDao findByEmail(String email); //{
//        return users.stream()
//                .filter(user -> user.getUsername().equals(email))
//                .findFirst()
//                .orElse(null);
//    }
}

// Compare this snippet from java/com/pavel/spring/springishere/repository/MySQLUserRepository.java: