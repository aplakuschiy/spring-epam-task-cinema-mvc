/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.services;

import com.epam.spring.core.htask.cinema.data.dao.db.TicketDao;
import com.epam.spring.core.htask.cinema.models.Event;
import com.epam.spring.core.htask.cinema.models.Ticket;
import com.epam.spring.core.htask.cinema.models.User;
import java.util.List;
import java.util.Set;

public class BookingService {

    DiscountService discountService;
    private TicketDao tickets;

    public TicketDao getTickets() {
        return tickets;
    }

    public void setTickets(TicketDao tickets) {
        this.tickets = tickets;
    }

     public DiscountService getDiscountService() {
        return discountService;
    }

    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    public float getTicketPrice(Event event, User user, int seat) {
        float basePrice = event.getBasePrice();
        List<Ticket> userTickets = tickets.getTicketsForUser(user);
        float countDiscount = (user != null) ? discountService.verifyDiscount(user, event, userTickets) : 0;
        return basePrice * (100 - countDiscount) / 100;
    }

    public float bookTicket(Event event, User user, Set<Integer> seats) {
        float sum = 0;
        List<Integer> seatsLock = tickets.getLockSeatsForEvent(event);
            for (Integer seat : seats) {
                if (!seatsLock.contains(seat)) {
                    float price = getTicketPrice(event, user, seat);
                    Ticket newTicket = new Ticket(event, user, seat, price);
                    tickets.create(newTicket);
                    sum += price;
                }
            }
        return sum;
    }
}
