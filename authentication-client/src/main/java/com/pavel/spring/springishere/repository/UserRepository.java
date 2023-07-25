package com.pavel.spring.springishere.repository;

import com.pavel.spring.springishere.dao.UserDao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    UserDao createUser(UserDao user);

    Optional<UserDao> findByEmail(String email);
}

