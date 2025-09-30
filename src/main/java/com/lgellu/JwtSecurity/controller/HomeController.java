package com.lgellu.JwtSecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/admin/hello")
    public String helloWorld() {
        return "ADMIN: Hello...";
    }

    @GetMapping("/user/hello")
    public String helloWorldUser() {
        return "USER: Hello...";
    }

}
