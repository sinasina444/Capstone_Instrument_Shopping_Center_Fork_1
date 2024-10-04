package com.fdu.capstone_instrument_shopping_center.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
public class UserController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
