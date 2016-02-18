/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.spring.core.htask.cinema.mvc.controllers;

import com.epam.spring.core.htask.cinema.mvc.services.LoadDataServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/booking")
public class BookingController {

    @Autowired
    LoadDataServices loadDataServices;
    
 
    @RequestMapping(value = "/multipleUpload", method = RequestMethod.GET)
    public String multiUpload() {
        return "uploadFiles";
    }


    @RequestMapping(value = "/multipleSave", method = RequestMethod.POST)
    public @ResponseBody
    String multipleSave(@RequestParam("files") MultipartFile[] files) {

        String msg = "";
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
            msg += loadDataServices.loadDataFromFile(files[i])+ "<br/>";
            }
            return msg;
        } else {
            return "Unable to upload. File is empty.";
        }
    }

}
