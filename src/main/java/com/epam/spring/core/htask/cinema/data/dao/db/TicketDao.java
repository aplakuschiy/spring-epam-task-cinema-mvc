package com.epam.spring.core.htask.cinema.data.dao.db;

import com.epam.spring.core.htask.cinema.models.Event;
import com.epam.spring.core.htask.cinema.models.Ticket;
import com.epam.spring.core.htask.cinema.models.User;
import java.util.List;


public interface TicketDao {

    List<Integer> getLockSeatsForEvent(Event event);
    List<Ticket> getTicketsForUser(User user);
    int create (Ticket item);
    Ticket get(int id);
    List<Ticket> findAll();

}