package com.epam.spring.core.htask.cinema.data.dao.db;

import com.epam.spring.core.htask.cinema.models.Event;
import com.epam.spring.core.htask.cinema.models.Ticket;
import com.epam.spring.core.htask.cinema.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(final User item) {
        final String sql = "insert into users (name, bdate) values(?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, item.getName());
                pst.setDate(2, new java.sql.Date(item.getDateBirth().getTime()));
                return pst;
            }
        },
                keyHolder);
        return (int) keyHolder.getKey();
    }

    @Override
    public User get(int id) {
        User user = jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                new Object[]{id},
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs,
                            int rowNum) throws SQLException {
                        User newUser = null;
                        if(rs != null)
                            newUser = new User(rs.getInt("id"), rs.getString("name"), rs.getDate("bdate"), 0);
                        return newUser;
                    }
                });
        return user;
    }

    @Override
    public List<User> getAll() {
      List<User> users = jdbcTemplate.query(
                "select * from users",
                new RowMapper<User>() {
                    public User mapRow(ResultSet rs,
                            int rowNum) throws SQLException {
                        return new User(rs.getInt("id"), rs.getString("name"), rs.getDate("bdate"), 0);
                    }
                });
        return users;
    }

}
