package com.captureit.controller;

import com.captureit.dao.GuestbookDao;
import com.captureit.model.GuestbookEntry;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guestbook")
@CrossOrigin(origins = "*")
public class GuestbookController {

    private final GuestbookDao guestbookDao;

    public GuestbookController(GuestbookDao guestbookDao) {
        this.guestbookDao = guestbookDao;
    }

    @GetMapping
    public List<GuestbookEntry> list() {
        return guestbookDao.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuestbookEntry create(@RequestBody GuestbookEntry entry) {
        return guestbookDao.save(entry);
    }
}
