package com.captureit.model;

import java.time.LocalDateTime;

public class GuestbookEntry {
    private Long id;
    private String name;
    private String relationship;
    private String message;
    private LocalDateTime createdAt;

    public GuestbookEntry() {}

    public GuestbookEntry(Long id, String name, String relationship, String message, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.relationship = relationship;
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRelationship() { return relationship; }
    public void setRelationship(String relationship) { this.relationship = relationship; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
