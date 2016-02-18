/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.mvc.controllers;

import com.epam.spring.core.htask.cinema.data.dao.db.UserDao;
import com.epam.spring.core.htask.cinema.models.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/users")
public class UsersController {

    @Autowired
    UserDao users;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ModelAndView getUsers() {
        List<User> lstUsers = users.getAll();
        return new ModelAndView("user", "users", lstUsers);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET, headers = "accept=application/pdf")
    public ModelAndView getUsersPdfWithHeaders() {
        ModelAndView model = new ModelAndView();
        List<User> lstUsers = users.getAll();
        model.addObject("users", lstUsers);
        model.setViewName("users.pdf");
        return model;
        //new ModelAndView("users.pdf", "users", lstUsers);
    }

    @RequestMapping(value = "/getAllPdf", method = RequestMethod.GET)
    public ModelAndView getUsersPdf() {
        ModelAndView model = new ModelAndView();
        List<User> lstUsers = users.getAll();
        model.addObject("users", lstUsers);
        model.setViewName("users.pdf");
        return model;
    }

    @RequestMapping(value = "/getAllJson", method = RequestMethod.GET)
    @ResponseBody
    public String getUsersJson() throws IOException {
        List<User> lstUsers = users.getAll();
        final OutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, lstUsers);
        return out.toString();
    }

}
