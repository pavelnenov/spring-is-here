package com.pavel.spring.springishere.db;

import com.pavel.spring.springishere.repository.MySQLUserRepository;
import com.pavel.spring.springishere.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class RepositoryConfig {

    @Bean
    public UserRepository userRepository(TransactionTemplate transactionTemplate, JdbcTemplate jdbcTemplate) {
        return new MySQLUserRepository(transactionTemplate, jdbcTemplate);
    }
}
