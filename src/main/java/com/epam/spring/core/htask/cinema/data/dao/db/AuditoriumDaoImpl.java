package com.epam.spring.core.htask.cinema.data.dao.db;

import com.epam.spring.core.htask.cinema.models.Auditorium;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class AuditoriumDaoImpl implements AuditoriumDao {

    JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int create(final Auditorium item) {
        final String sql = "insert into auditoriums (name, countseats) values(?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                        pst.setString(1, item.getName());
                        pst.setInt(2, item.getCountSeats());
                        return pst;
                    }
                },
                keyHolder);
        int key = (int) keyHolder.getKey();
        //добавляем VIP места
        Collection<Integer> vipSeats = item.getVipSeats();
        for (Integer seat : vipSeats) {
            jdbcTemplate.update("insert into vipseats (name, idauditorium) values(?,?)",
                    seat, key);
        }
        return key;
    }

    @Override
    public Auditorium get(int id) {
        Auditorium auditorium = jdbcTemplate.queryForObject(
                "select * from auditoriums where id = ?",
                new Object[]{id},
                new RowMapper<Auditorium>() {
                    public Auditorium mapRow(ResultSet rs,
                            int rowNum) throws SQLException {
                        Auditorium newAuditorium = new Auditorium(rs.getInt("id"), rs.getString("name"), rs.getInt("countseats"), null);
                        return newAuditorium;
                    }
                });
        return auditorium;
    }
}
