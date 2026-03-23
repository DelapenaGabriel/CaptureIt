package com.captureit.dao;

import com.captureit.model.Rsvp;
import java.util.List;
import java.util.Optional;

public interface RsvpDao {
    List<Rsvp> findAll();
    Optional<Rsvp> findById(Long id);
    Rsvp save(Rsvp rsvp);
}
