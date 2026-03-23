package com.captureit.dao;

import com.captureit.model.GalleryImage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcGalleryImageDao implements GalleryImageDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcGalleryImageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GalleryImage> findAll() {
        List<GalleryImage> images = new ArrayList<>();
        String sql = "SELECT id, url, caption, uploaded_by, uploaded_at FROM gallery_images ORDER BY uploaded_at DESC";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            images.add(mapRowToImage(results));
        }
        return images;
    }

    @Override
    public GalleryImage save(GalleryImage image) {
        String sql = "INSERT INTO gallery_images (url, caption, uploaded_by) VALUES (?, ?, ?) RETURNING id, uploaded_at";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, image.getUrl(), image.getCaption(), image.getUploadedBy());
        if (result.next()) {
            image.setId(result.getLong("id"));
            if (result.getTimestamp("uploaded_at") != null) {
                image.setUploadedAt(result.getTimestamp("uploaded_at").toLocalDateTime());
            }
        }
        return image;
    }

    private GalleryImage mapRowToImage(SqlRowSet rs) {
        GalleryImage image = new GalleryImage();
        image.setId(rs.getLong("id"));
        image.setUrl(rs.getString("url"));
        image.setCaption(rs.getString("caption"));
        image.setUploadedBy(rs.getString("uploaded_by"));
        if (rs.getTimestamp("uploaded_at") != null) {
            image.setUploadedAt(rs.getTimestamp("uploaded_at").toLocalDateTime());
        }
        return image;
    }
}
