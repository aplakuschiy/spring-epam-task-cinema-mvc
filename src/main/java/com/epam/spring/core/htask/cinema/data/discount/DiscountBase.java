/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.data.discount;

import com.epam.spring.core.htask.cinema.models.Event;
import com.epam.spring.core.htask.cinema.models.Ticket;
import com.epam.spring.core.htask.cinema.models.User;
import java.util.List;

/**
 *
 * @author Oleksandr_Plakushchy
 */
public abstract class DiscountBase {
    float percent;

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
    
    public abstract float getDiscount(User user, Event event, List<Ticket> tickets);
}
