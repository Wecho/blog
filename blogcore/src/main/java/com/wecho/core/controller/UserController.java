package com.wecho.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller()
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "{userID}",method = RequestMethod.GET)
    public String getUser(@PathVariable("userID") Long userId){
        return "main/index";
    }
}
