package com.captureit.model;

import java.time.LocalDateTime;

public class GalleryImage {
    private Long id;
    private String url;
    private String caption;
    private String uploadedBy;
    private LocalDateTime uploadedAt;

    public GalleryImage() {}

    public GalleryImage(Long id, String url, String caption, String uploadedBy, LocalDateTime uploadedAt) {
        this.id = id;
        this.url = url;
        this.caption = caption;
        this.uploadedBy = uploadedBy;
        this.uploadedAt = uploadedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }
}
