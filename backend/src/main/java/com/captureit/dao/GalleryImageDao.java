package com.captureit.dao;

import com.captureit.model.GalleryImage;
import java.util.List;

public interface GalleryImageDao {
    List<GalleryImage> findAll();
    GalleryImage save(GalleryImage image);
}
