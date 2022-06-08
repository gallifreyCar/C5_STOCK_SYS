package com.Gallifrey.springboot00.contronller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestRequestController {
    @RequestMapping("/test")
    public String test(){
        return "ok";
    }
}
