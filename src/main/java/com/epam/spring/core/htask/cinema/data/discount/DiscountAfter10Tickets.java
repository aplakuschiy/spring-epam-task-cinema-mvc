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
public class DiscountAfter10Tickets extends DiscountBase {

    @Override
    public float getDiscount(User user, Event event, List<Ticket> tickets) {
        if (tickets != null && user != null) {
           int countTickets = 0;
            for (Ticket ticket : tickets) {
                if (ticket.getUser()!=null && ticket.getUser().equals(user)) {
                    countTickets++;
                    if (countTickets > 10) {
                        return percent;
                    }
                }
            }
        }
        return 0;
    }

}
