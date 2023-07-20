package com.pavel.spring.springishere.repository;

import com.pavel.spring.springishere.dao.UserDao;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<UserDao> {

    @Override
    public UserDao mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserDao(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password")
        );
    }
}
