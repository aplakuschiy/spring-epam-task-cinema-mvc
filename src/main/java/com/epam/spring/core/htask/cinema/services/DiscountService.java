/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.services;

import com.epam.spring.core.htask.cinema.data.discount.DiscountBase;
import com.epam.spring.core.htask.cinema.models.Event;
import com.epam.spring.core.htask.cinema.models.Ticket;
import com.epam.spring.core.htask.cinema.models.User;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Oleksandr_Plakushchy
 */
public class DiscountService {

    private Collection<DiscountBase> listDiscounts;

    public DiscountService(Collection<DiscountBase> listDiscounts) {
        this.listDiscounts = listDiscounts;
    }

    
    public DiscountService() {
    }

    public Collection<DiscountBase> getListDiscounts() {
        return listDiscounts;
    }

    public void setListDiscounts(Collection<DiscountBase> listDiscounts) {
        this.listDiscounts = listDiscounts;
    }

    public float verifyDiscount(User user, Event event, List<Ticket> tickets) {
        if (user != null) {
            List<Float> percentDiscount = new CopyOnWriteArrayList<>();
            for (DiscountBase discount : listDiscounts) {
                percentDiscount.add(discount.getDiscount(user, event, tickets));
            }
            Collections.sort(percentDiscount);
            return percentDiscount.get(percentDiscount.size() - 1);
        }
        return 0;
    }

}
