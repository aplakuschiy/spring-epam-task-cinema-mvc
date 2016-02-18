package com.epam.spring.core.htask.cinema.data.dao.db;

import com.epam.spring.core.htask.cinema.models.Auditorium;


public interface AuditoriumDao {

    int create (Auditorium item);
    Auditorium get(int id);

}