package com.BookStore.OnlineBookExchange.controller;


import com.BookStore.OnlineBookExchange.DTOs.UserDTO;
import com.BookStore.OnlineBookExchange.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {


    final private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute UserDTO userDTO) {
        userService.register(userDTO);

        return "redirect:/login";

    }
}
