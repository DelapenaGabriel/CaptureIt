package com.captureit.dao;

import com.captureit.model.GuestbookEntry;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcGuestbookDao implements GuestbookDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcGuestbookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GuestbookEntry> findAll() {
        List<GuestbookEntry> entries = new ArrayList<>();
        String sql = "SELECT id, name, relationship, message, created_at FROM guestbook_entries ORDER BY created_at DESC";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            entries.add(mapRowToEntry(results));
        }
        return entries;
    }

    @Override
    public GuestbookEntry save(GuestbookEntry entry) {
        String sql = "INSERT INTO guestbook_entries (name, relationship, message) VALUES (?, ?, ?) RETURNING id, created_at";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, entry.getName(), entry.getRelationship(), entry.getMessage());
        if (result.next()) {
            entry.setId(result.getLong("id"));
            if (result.getTimestamp("created_at") != null) {
                entry.setCreatedAt(result.getTimestamp("created_at").toLocalDateTime());
            }
        }
        return entry;
    }

    private GuestbookEntry mapRowToEntry(SqlRowSet rs) {
        GuestbookEntry entry = new GuestbookEntry();
        entry.setId(rs.getLong("id"));
        entry.setName(rs.getString("name"));
        entry.setRelationship(rs.getString("relationship"));
        entry.setMessage(rs.getString("message"));
        if (rs.getTimestamp("created_at") != null) {
            entry.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        return entry;
    }
}
