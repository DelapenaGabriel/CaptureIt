package com.captureit.model;

public class Rsvp {
    private Long id;
    private String name;
    private String email;
    private boolean attending;
    private int guests;
    private String songRequest;
    private String message;

    public Rsvp() {}

    public Rsvp(Long id, String name, String email, boolean attending, int guests, String songRequest, String message) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.attending = attending;
        this.guests = guests;
        this.songRequest = songRequest;
        this.message = message;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public boolean isAttending() { return attending; }
    public void setAttending(boolean attending) { this.attending = attending; }
    
    public int getGuests() { return guests; }
    public void setGuests(int guests) { this.guests = guests; }

    public String getSongRequest() { return songRequest; }
    public void setSongRequest(String songRequest) { this.songRequest = songRequest; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
