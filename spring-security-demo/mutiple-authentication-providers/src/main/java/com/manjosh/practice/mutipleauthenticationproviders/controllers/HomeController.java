package com.manjosh.practice.mutipleauthenticationproviders.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "hello home1";
    }

    @GetMapping("/private")
    public String secure(){
        return "secured";
    }
}
