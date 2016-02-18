/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.models;

import java.util.Random;

public class Ticket extends BaseModel{
    private Event event;
    private User user;
    private int seat;
    private float price;
    
    public Ticket(Event event, User user, int seat, float price) {
        Random random = new Random();
        super.id = random.nextInt();
        super.name = "";
        this.event = event;
        this.user = user;
        this.seat = seat;
        this.price = price;
    }

    public Ticket(int id, String name, Event event, User user, int seat, float price) {
        if(id<=0)
        {
            Random random = new Random();
            id=random.nextInt();
        }
        super.id = id;
        super.name = name;
        this.event = event;
        this.user = user;
        this.seat = seat;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Event getEvent() {
        return event;
    }

    public int getSeat() {
        return seat;
    }

    public User getUser() {
        return user;
    }

    public float getPrice() {
        return price;
    }
    
    
    
}
