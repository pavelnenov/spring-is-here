package com.pavel.spring.springishere.repository;

import com.pavel.spring.springishere.dao.UserDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;


public class MySQLUserRepository implements UserRepository {

    private final TransactionTemplate transactionTemplate;
    private final JdbcTemplate jdbcTemplate;

    private final String INSERT_USER = """
            INSERT INTO users (first_name, last_name, email, password)
            VALUES (?, ?, ?, ?)
            """;

    private final String GET_USER_BY_EMAIL = """
            SELECT * FROM users
            WHERE email = ?
            """;


    public MySQLUserRepository(TransactionTemplate transactionTemplate, JdbcTemplate jdbcTemplate) {
        this.transactionTemplate = transactionTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserDao findByEmail(String email) {
        return transactionTemplate.execute(
                status -> jdbcTemplate.queryForObject(
                        GET_USER_BY_EMAIL,
                        new UserRowMapper(),
                        email
                )
        );
    }

    @Override
    public UserDao createUser(UserDao user) {
        return transactionTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.firstName());
                ps.setString(2, user.lastName());
                ps.setString(3, user.email());
                ps.setString(4, user.password());
                return ps;
            }, keyHolder);

            return user;
        });
    }
}
