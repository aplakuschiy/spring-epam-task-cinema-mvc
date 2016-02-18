/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.aop;

import com.epam.spring.core.htask.cinema.models.Event;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 *
 * @author Oleksandr_Plakushchy
 */
@Aspect
public class CounterAspect {
    
    private boolean callTimeInterval;

    private Map<String, Integer> counter;

    Date dateBeginGetTicketPrice;
    long startGetTicketPrice;

    public CounterAspect(boolean callTimeInterval) {
        this.callTimeInterval = callTimeInterval;
    }
    
     public Map<String, Integer> getCounter() {
        return counter;
    }

    @Pointcut("execution(* com.epam.spring.core.htask.cinema.services.BookingService.bookTicket(..))")
    private void allBookTicketMethods() {
    }

    @AfterReturning("allBookTicketMethods()")
    public void count(JoinPoint jp) {
        if (counter == null) {
            counter = new HashMap<>();
        }
        //считаем купленные билеты
            addCounter("куплено билетов");

        Object[] args = jp.getArgs();

        if (args != null)
        {
            // счетчик конкретного события Event по имени
            if(args[0] != null) {
             String nameEvent = "event: "+((Event) args[0]).getName();
             addCounter(nameEvent);
            }
            // счетчик обращений к системе определения индивидуальной цены
            if(args[1] != null) {
             addCounter("индивидуальная цена");
            }        
        }
      
        
    }

    private void addCounter(String key) {
        if (!counter.containsKey(key)) {
            counter.put(key, 0);
        }
        counter.put(key, counter.get(key) + 1);
    }

    
    //статистика времени выполнения метода bookTicket
    @Pointcut("execution(* com.epam.spring.core.htask.cinema.services.BookingService.bookTicket(..))")
    private void allGetTicketPriceMethods() {
    }

    @Before("allGetTicketPriceMethods()")
    public void logBefore() {
        dateBeginGetTicketPrice = Calendar.getInstance().getTime();
        startGetTicketPrice = System.nanoTime();
    }

    @AfterReturning("allGetTicketPriceMethods()")
    public void journal(JoinPoint jp) {
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss.SSS");
        if(callTimeInterval==true)
            System.out.println("перехват : " + jp.getSignature() + " старт - " + df.format(dateBeginGetTicketPrice) + " вр.вып.-" + (System.nanoTime() - startGetTicketPrice) + " ns");

    }

}
