/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.mvc.services;

import com.epam.spring.core.htask.cinema.data.dao.db.AuditoriumDao;
import com.epam.spring.core.htask.cinema.data.dao.db.EventDao;
import com.epam.spring.core.htask.cinema.data.dao.db.UserDao;
import com.epam.spring.core.htask.cinema.models.Auditorium;
import com.epam.spring.core.htask.cinema.models.Event;
import com.epam.spring.core.htask.cinema.models.User;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LoadDataServices {

    @Autowired
    EventDao events;
    @Autowired
    UserDao users;
    @Autowired
    AuditoriumDao auditoriums;

    public String loadDataFromFile(MultipartFile file) {
        StringBuilder msg = new StringBuilder();
        String fileName = null;
        try {
            fileName = file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            String strFile = new String(bytes);
            ObjectMapper mapper = new ObjectMapper();
            if (strFile.contains("dateBirth"))//user-а
            {
                List<User> users = null;
                users = mapper.readValue(strFile, new TypeReference<List<User>>() {
                });
                for (User user : users) {
                    msg.append("<br />").append(addUserDb(user));
                }
                msg.append("<br />You upload ").append(fileName).append(": ").append(users.size());
            }
            if (strFile.contains("basePrice"))//event-ы
            {
                List<Event> events = null;
                events = mapper.readValue(strFile, new TypeReference<List<Event>>() {
                });
                for (Event event : events) {
                    msg.append("<br />").append(addEventDb(event));
                }
                msg.append("<br />You upload ").append(fileName).append(": ").append(events.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            msg.append("<br />You failed to upload ").append(fileName).append(": ").append(e.getMessage());
        }
        return msg.toString();
    }

    String addUserDb(User user) {
        StringBuilder msg = new StringBuilder("user " + user.getId());
        User findUser = null;
        try
        {
            findUser = users.get(user.getId());
            if (findUser != null) 
                msg.append(" already exists");
        }
        catch(org.springframework.dao.EmptyResultDataAccessException e)
        {
              users.create(user);
              msg.append(" successfully added");
        }
        return msg.toString();
    }

    String addEventDb(Event event) {
        StringBuilder msg = new StringBuilder();
        //Ищем связанный зал, если такого нет, добавляем
        Auditorium auditorium = event.getAuditorium();
        int idAuditorium = auditorium.getId();
        Auditorium findAuditorium = null;
        try
        {
            findAuditorium = auditoriums.get(idAuditorium);
            msg.append("auditoriums ").append(findAuditorium.getId()).append(" already exists");
        }
        catch(org.springframework.dao.EmptyResultDataAccessException e)
        {
              auditoriums.create(auditorium);
              msg.append("auditoriums ").append(idAuditorium).append("  successfully added");
        }
        
        //Добавляем событие, если его нет
        Event findEvent = null;
        try
        {
            findEvent = events.get(event.getId());
            msg.append("<br /> - event ").append(findEvent.getId()).append(" already exists");
        }
        catch(org.springframework.dao.EmptyResultDataAccessException e)
        {
              events.create(event);
              msg.append("<br /> - event ").append(event.getId()).append("  successfully added");
        }
       
        return msg.toString();
    }

}
