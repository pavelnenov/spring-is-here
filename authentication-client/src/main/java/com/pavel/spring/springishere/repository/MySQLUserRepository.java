package com.pavel.spring.springishere.repository;

import com.pavel.spring.springishere.dao.UserDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;


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
    public Optional<UserDao> findByEmail(String email) {
        try {
            UserDao user = transactionTemplate.execute(
                    status -> jdbcTemplate.queryForObject(
                            GET_USER_BY_EMAIL,
                            new UserRowMapper(),
                            email
                    )
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public UserDao createUser(UserDao user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return transactionTemplate.execute(status -> {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(
                        INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.firstName());
                ps.setString(2, user.lastName());
                ps.setString(3, user.email());
                ps.setString(4, passwordEncoder.encode(user.password()));
                return ps;
            }, keyHolder);

            return user;
        });
    }
}
