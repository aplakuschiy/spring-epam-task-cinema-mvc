package com.epam.spring.core.htask.cinema.data.dao.db;

import com.epam.spring.core.htask.cinema.models.Auditorium;
import com.epam.spring.core.htask.cinema.models.Event;
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
public class EventDaoImpl implements EventDao {

    JdbcTemplate jdbcTemplate;
    AuditoriumDao auditoriumDao;

    public EventDaoImpl(JdbcTemplate jdbcTemplate, AuditoriumDao auditoriumDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.auditoriumDao = auditoriumDao;
    }

    @Override
    public int create(final Event item) {
        final String sql = "insert into events (name, dateevent, baseprice, idauditorium) values(?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pst.setString(1, item.getName());
                pst.setDate(2, new java.sql.Date(item.getDateEvent().getTime()));
                pst.setFloat(3, item.getBasePrice());
                Auditorium auditorium = item.getAuditorium();
                if (auditorium != null) {
                    pst.setInt(4, auditorium.getId());
                }
                return pst;
            }
        },
                keyHolder);
        int key = (int) keyHolder.getKey();
        return key;
    }

    @Override
    public Event get(int id) {
        Event auditorium = jdbcTemplate.queryForObject(
                "select * from events where id = ?",
                new Object[]{id},
                new RowMapper<Event>() {
            public Event mapRow(ResultSet rs,
                    int rowNum) throws SQLException {
                Auditorium auditorium = auditoriumDao.get(rs.getInt("idauditorium"));
                Event newEvent = new Event(rs.getInt("id"), rs.getString("name"), (java.util.Date) rs.getTimestamp("dateevent"), rs.getFloat("baseprice"), auditorium);
                return newEvent;
            }
        });
        return auditorium;
    }

    @Override
    public List<Event> getAll() {
        List<Event> users = jdbcTemplate.query(
                "select * from events",
                new RowMapper<Event>() {
            public Event mapRow(ResultSet rs,
                    int rowNum) throws SQLException {
                   Auditorium auditorium = auditoriumDao.get(rs.getInt("idauditorium"));
                   Event newEvent = new Event(rs.getInt("id"), rs.getString("name"), (java.util.Date) rs.getTimestamp("dateevent"), rs.getFloat("baseprice"), auditorium);
                   return newEvent;
             }
        });
        return users;
    }

}
