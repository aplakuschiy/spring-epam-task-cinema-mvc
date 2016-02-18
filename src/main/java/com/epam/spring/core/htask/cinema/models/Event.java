/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Oleksandr_Plakushchy
 */
public class Event extends BaseModel {

    private Date dateEvent;
    private float basePrice;
    private Auditorium auditorium;

    public Event() {
    }

    public Event(int id, String name, Date dateEvent, float basePrice, Auditorium auditorium) {
        super.id = id;
        super.name = name;
        this.dateEvent = dateEvent;
        this.basePrice = basePrice;
        this.auditorium = auditorium;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateEvent() {
        return dateEvent;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    @Override
    public String toString() {
       return name+" (нач.сеанса ->"+new SimpleDateFormat("HH:mm dd.MM.yyyy").format(dateEvent) + " - "+auditorium.name+" )";
    }
    
    
}
