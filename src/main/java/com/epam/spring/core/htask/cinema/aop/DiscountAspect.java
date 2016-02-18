/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.aop;

import com.epam.spring.core.htask.cinema.models.Event;
import com.epam.spring.core.htask.cinema.models.User;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class DiscountAspect {

 
    private Map<String, CountersDiscount> counter;
    
    public DiscountAspect() {
            counter = new HashMap<>();
    }

    public Map<String, CountersDiscount> getCounter() {
        return counter;
    }
    
    @Pointcut("execution(* com.epam.spring.core.htask.cinema.services.DiscountService.verifyDiscount(..))")
    private void allVerifyDiscountMethods() {
    }
     

    @AfterReturning(pointcut = "allVerifyDiscountMethods()", returning = "result")
    public void count(JoinPoint jp, float result) {
        //Подсчитываем полученную выгоду
        Object[] args = jp.getArgs();
        if (args != null && args[1] != null && args[0] != null) {
            String nameUser = ((User) args[0]).getName();
            Event event = (Event) args[1];
            float sumDiscount = event.getBasePrice() * result / 100;
            if (sumDiscount != 0) {
                if (!counter.containsKey(nameUser)) {
                    counter.put(nameUser, new CountersDiscount(0, 0));
                }
                CountersDiscount old = counter.get(nameUser);
                CountersDiscount newCountersDiscount = new CountersDiscount(old.getCount() + 1, old.getSum() + sumDiscount);
                counter.put(nameUser, newCountersDiscount);

            }
        }
    }
}
