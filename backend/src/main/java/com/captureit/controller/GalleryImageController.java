package com.captureit.controller;

import com.captureit.dao.GalleryImageDao;
import com.captureit.model.GalleryImage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gallery")
@CrossOrigin(origins = "*")
public class GalleryImageController {

    private final GalleryImageDao galleryImageDao;

    public GalleryImageController(GalleryImageDao galleryImageDao) {
        this.galleryImageDao = galleryImageDao;
    }

    @GetMapping
    public List<GalleryImage> list() {
        return galleryImageDao.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GalleryImage create(@RequestBody GalleryImage image) {
        return galleryImageDao.save(image);
    }
}
