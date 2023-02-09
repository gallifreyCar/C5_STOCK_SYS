package com.Gallifrey.springboot00.contronller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestRequestController {
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        return "ok";
    }
}
