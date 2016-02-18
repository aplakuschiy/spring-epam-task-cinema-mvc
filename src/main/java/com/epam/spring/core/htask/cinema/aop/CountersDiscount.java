/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.aop;

public class CountersDiscount {
    private int count;
    private float sum;

    public CountersDiscount(int count, float sum) {
        this.count = count;
        this.sum = sum;
    }

    public int getCount() {
        return count;
    }

    public float getSum() {
        return sum;
    }
    
}
