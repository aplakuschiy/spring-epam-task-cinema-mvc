/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.mvc.controllers;

import java.io.IOException;
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@Controller  
public class HomeController {  
  
    @RequestMapping(value={"/","/index","/home"}, method = RequestMethod.GET)  
    public String viewHome(){  
        return "home";  
    }  
    
    @RequestMapping(value = "/testException", method = RequestMethod.GET)  //getTicketPrice
    public void testException() throws Exception {
        int k = 10/0; // div by zerro
//        throw new IOException("My problem");
    }

}  