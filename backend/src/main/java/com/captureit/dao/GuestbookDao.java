package com.captureit.dao;

import com.captureit.model.GuestbookEntry;
import java.util.List;

public interface GuestbookDao {
    List<GuestbookEntry> findAll();
    GuestbookEntry save(GuestbookEntry entry);
}
