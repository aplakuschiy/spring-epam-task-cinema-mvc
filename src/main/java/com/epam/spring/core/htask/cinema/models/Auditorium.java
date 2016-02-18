/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.models;

import java.util.Collection;

/**
 *
 * @author Oleksandr_Plakushchy
 */
public class Auditorium extends BaseModel{
    private int countSeats;
    private Collection<Integer> vipSeats;

    public Auditorium() {
    }
    
    public Auditorium(int id, String name, int countSeats, Collection<Integer> vipSeats) {
        super.id = id;
        super.name = name;
        this.countSeats = countSeats;
        this.vipSeats = vipSeats;
    }
    
    public Collection<Integer> getVipSeats() {
        return vipSeats;
    }

    public int getCountSeats() {
        return countSeats;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
    
    
}
