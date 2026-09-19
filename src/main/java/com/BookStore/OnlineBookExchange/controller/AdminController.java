package com.BookStore.OnlineBookExchange.controller;

import com.BookStore.OnlineBookExchange.entity.User;
import com.BookStore.OnlineBookExchange.repo.UserRepo;
import com.BookStore.OnlineBookExchange.service.ListingService;
import com.BookStore.OnlineBookExchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ListingService listingService;
    private final UserService userService;

    @GetMapping("/admin")
    public String adminPage(Model model) {

        User currentUser = userService.getCurrentUser();

        List<User> users = userService.getAllUsers()
                .stream()
                .filter(user -> !user.getId().equals(currentUser.getId()))
                .toList();

        model.addAttribute("users", users);

        return "admin";
    }

    @PostMapping("/admin/users/{userId}/toggle-block")
    public String toggleBlockUser(@PathVariable Long userId) {
        userService.toggleBlockUser(userId);
        return "redirect:/admin";
    }

    @GetMapping("/admin/listings")
    public String allListings(Model model) {
        model.addAttribute("listings", listingService.getAllListings());
        return "admin/listings";
    }

    @PostMapping("/admin/listings/{listingId}/delete")
    public String deleteListing(@PathVariable Long listingId) {
        listingService.deleteListingByAdmin(listingId);
        return "redirect:/admin/listings";
    }


}
