package com.epam.spring.core.htask.cinema;

import com.epam.spring.core.htask.cinema.aop.CounterAspect;
import com.epam.spring.core.htask.cinema.aop.CountersDiscount;
import com.epam.spring.core.htask.cinema.aop.DiscountAspect;
import com.epam.spring.core.htask.cinema.data.dao.db.AuditoriumDao;
import com.epam.spring.core.htask.cinema.data.dao.db.EventDao;
import com.epam.spring.core.htask.cinema.data.dao.db.TicketDao;
import com.epam.spring.core.htask.cinema.data.dao.db.UserDao;
import com.epam.spring.core.htask.cinema.models.Auditorium;
import com.epam.spring.core.htask.cinema.models.Event;
import com.epam.spring.core.htask.cinema.models.Ticket;
import com.epam.spring.core.htask.cinema.models.User;
import com.epam.spring.core.htask.cinema.services.BookingService;
import com.epam.spring.core.htask.cinema.services.DiscountService;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    private UserDao users;
    private AuditoriumDao auditoriums;
    private EventDao events;
    private TicketDao tickets;
    

    private DiscountService discountService;
    private BookingService bookingService;

    static ConfigurableApplicationContext ctx;
    static App app;

    public App(UserDao users, AuditoriumDao auditoriums, EventDao events, TicketDao tickets) {
        this.users = users;
        this.auditoriums = auditoriums;
        this.events = events;
        this.tickets = tickets;
    }

    public DiscountService getDiscountService() {
        return discountService;
    }

    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }

    public BookingService getBookingService() {
        return bookingService;
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void initTestData() throws ParseException {
        //тестовый набор User
        DateFormat dfBirth = new SimpleDateFormat("dd.MM.yyyy");
        app.users.create(new User(1, "Вася Пупкин", dfBirth.parse("25.10.1990"), 1));
        app.users.create(new User(2, "Петя Дуб", dfBirth.parse("12.11.1990"), 1));
        app.users.create(new User(3, "Дуся Лесная", dfBirth.parse("13.11.1990"), 1));


        //тестовый набор Auditorium
        app.auditoriums.create(new Auditorium(1, "Красный зал", 100, Arrays.asList(new Integer[]{1, 2, 3, 4})));
        app.auditoriums.create(new Auditorium(2, "Синий зал", 150, Arrays.asList(new Integer[]{1, 2, 3, 4, 100, 150})));
        app.auditoriums.create(new Auditorium(3, "Зеленый зал", 50, Arrays.asList(new Integer[]{1, 2, 3, 4, 40, 45})));

        //тестовый набор Event
        DateFormat dfEvent = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        app.events.create(new Event(1, "Терминатор 5", dfEvent.parse("25.10.2015 9:00"), 50, app.auditoriums.get(1)));
        app.events.create(new Event(2, "Терминатор 5", dfEvent.parse("25.10.2015 13:00"), 50, app.auditoriums.get(1)));
        app.events.create(new Event(3, "Терминатор 5", dfEvent.parse("25.10.2015 17:00"), 50, app.auditoriums.get(1)));
        app.events.create(new Event(4, "Смурфики", dfEvent.parse("25.10.2015 10:00"), 30, app.auditoriums.get(2)));
        app.events.create(new Event(5, "Смурфики", dfEvent.parse("25.10.2015 18:00"), 30, app.auditoriums.get(0)));
    }

    public void generateTestDatabaseTickets(int countTickets) {
        Random rand = new Random();
        for (int i = 0; i <= countTickets; i++) {

            //каждый 3й - не зарегистрированный юзер
            User user = (i % 3 == 0) ? null : users.get(rand.nextInt(3));

            Event event = events.get(rand.nextInt(5));
            
            Auditorium a = auditoriums.get(event.getAuditorium().getId());
            int seat = 0; //место
            List<Integer> lockSeats = tickets.getLockSeatsForEvent(event);
            do {
                seat = rand.nextInt(a.getCountSeats());
            } while (lockSeats != null && lockSeats.contains(seat));

            Set<Integer> seats = new HashSet<>();
            seats.add(seat);
            bookingService.bookTicket(event, user, seats);
        }
    }

    public static void main(String[] args) throws ParseException, IOException {

        ctx = new ClassPathXmlApplicationContext(new String[]{"cinema-spring.xml"});
        app = (App) ctx.getBean("app");
        app.initTestData();
        app.generateTestDatabaseTickets(100);

        //Выводим все сгенерированные билеты
        System.out.println(" ==== Купленные билеты ==== ");
        int num = 0;
        List<Ticket> listTickets = app.tickets.findAll();
        for (Ticket t : listTickets) {
            System.out.println(++num + " >> " + t.getEvent() + " - Клиент-> " + ((t.getUser() != null) ? t.getUser().getName() : null) + " - Место->" + t.getSeat() + " = Цена->" + t.getPrice());
        }

        //Выводим историю по User-у 
        num = 0;
        List<Ticket> userTickets = app.tickets.getTicketsForUser(/*null*/app.users.get(1)); //null - клиент не зарегистрирован 
        System.out.println(" ==== История юзера ==== ");
        for (Ticket t : userTickets) {
            System.out.println(++num + " >> " + t.getEvent() + " ->" + ((t.getUser() != null) ? t.getUser().getName() : null) + " - место->" + t.getSeat() + " = цена бил.->" + t.getPrice());
        }

        //Выводим счетчики
        System.out.println("==== Статистика AOP (CounterAspect)====");
        CounterAspect counterAspect = (CounterAspect) ctx.getBean("counterAspect");
        for (Map.Entry<String, Integer> e : counterAspect.getCounter().entrySet()) {
            System.out.print(e.getKey());
            System.out.println(" = " + e.getValue());
        }

        System.out.println("==== Статистика AOP (DiscountAspect)====");
        DiscountAspect discountAspect = (DiscountAspect) ctx.getBean("discountAspect");
        for (Map.Entry<String, CountersDiscount> e : discountAspect.getCounter().entrySet()) {
            CountersDiscount countersDiscount = e.getValue();
            System.out.print(e.getKey());
            System.out.println(" = воспользовался скидками " + countersDiscount.getCount() + " раз на сумму " + countersDiscount.getSum());
        }

        System.in.read();
        
        ctx.close();
    }

}
