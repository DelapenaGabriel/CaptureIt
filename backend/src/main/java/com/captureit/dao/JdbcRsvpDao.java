package com.captureit.dao;

import com.captureit.model.Rsvp;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRsvpDao implements RsvpDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcRsvpDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Rsvp> findAll() {
        List<Rsvp> rsvps = new ArrayList<>();
        String sql = "SELECT id, name, email, attending, guests, song_request, message FROM rsvps ORDER BY id ASC";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            rsvps.add(mapRowToRsvp(results));
        }
        return rsvps;
    }

    @Override
    public Optional<Rsvp> findById(Long id) {
        String sql = "SELECT id, name, email, attending, guests, song_request, message FROM rsvps WHERE id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return Optional.of(mapRowToRsvp(results));
        }
        return Optional.empty();
    }

    @Override
    public Rsvp save(Rsvp rsvp) {
        String sql = "INSERT INTO rsvps (name, email, attending, guests, song_request, message) " +
                     "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(sql, Long.class, 
                rsvp.getName(), rsvp.getEmail(), rsvp.isAttending(), rsvp.getGuests(), rsvp.getSongRequest(), rsvp.getMessage());
        rsvp.setId(id);
        return rsvp;
    }

    private Rsvp mapRowToRsvp(SqlRowSet rs) {
        Rsvp rsvp = new Rsvp();
        rsvp.setId(rs.getLong("id"));
        rsvp.setName(rs.getString("name"));
        rsvp.setEmail(rs.getString("email"));
        rsvp.setAttending(rs.getBoolean("attending"));
        rsvp.setGuests(rs.getInt("guests"));
        rsvp.setSongRequest(rs.getString("song_request"));
        rsvp.setMessage(rs.getString("message"));
        return rsvp;
    }
}
