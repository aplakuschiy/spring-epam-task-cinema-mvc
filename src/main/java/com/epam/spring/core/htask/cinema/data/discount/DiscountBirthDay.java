/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.data.discount;

import com.epam.spring.core.htask.cinema.models.Event;
import com.epam.spring.core.htask.cinema.models.Ticket;
import com.epam.spring.core.htask.cinema.models.User;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Oleksandr_Plakushchy
 */
public class DiscountBirthDay extends DiscountBase {

    @Override
    public float getDiscount(User user, Event event, List<Ticket> tickets) {
        if (user != null && event != null) {
            Date dateBirth = user.getDateBirth();
            Date dateEvent = event.getDateEvent();
            SimpleDateFormat df = new SimpleDateFormat("dd.MM");
            if (df.format(dateBirth).equals(df.format(dateEvent))) {
                return percent;
            }
        }
        return 0;
    }
}
