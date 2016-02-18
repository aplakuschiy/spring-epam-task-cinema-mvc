package com.epam.spring.core.htask.cinema.data.dao.db;

import com.epam.spring.core.htask.cinema.models.Event;
import java.util.List;

public interface EventDao {
    int create(Event item);
    Event get(int id);
    List<Event> getAll();
}
