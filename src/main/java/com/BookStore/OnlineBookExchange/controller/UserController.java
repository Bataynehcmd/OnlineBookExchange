package com.BookStore.OnlineBookExchange.controller;


import com.BookStore.OnlineBookExchange.DTOs.UpdateProfileDTO;
import com.BookStore.OnlineBookExchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("user", userService.getCurrentUser());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @ModelAttribute UpdateProfileDTO dto) {

        userService.updateProfile(dto);

        return "redirect:/home";
    }

}
