package com.example.springsecuritypractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/home")
    public String getString()
    {
        return "Hello world";
    }
    @GetMapping("/hello")
    public String getString1()
    {
        return "Hello world1";
    }
    @GetMapping("/users")
    public String getString2()
    {
        return "welcome home";
    }
}
