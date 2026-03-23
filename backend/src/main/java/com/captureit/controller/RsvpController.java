package com.captureit.controller;

import com.captureit.dao.RsvpDao;
import com.captureit.model.Rsvp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/rsvps")
@CrossOrigin(origins = "*")
public class RsvpController {

    private final RsvpDao rsvpDao;

    public RsvpController(RsvpDao rsvpDao) {
        this.rsvpDao = rsvpDao;
    }

    @GetMapping
    public List<Rsvp> list() {
        return rsvpDao.findAll();
    }

    @GetMapping("/{id}")
    public Rsvp get(@PathVariable Long id) {
        return rsvpDao.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RSVP not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rsvp create(@RequestBody Rsvp rsvp) {
        return rsvpDao.save(rsvp);
    }
}
