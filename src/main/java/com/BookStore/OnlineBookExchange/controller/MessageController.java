package com.BookStore.OnlineBookExchange.controller;

import com.BookStore.OnlineBookExchange.entity.Listing;
import com.BookStore.OnlineBookExchange.entity.Message;
import com.BookStore.OnlineBookExchange.entity.User;
import com.BookStore.OnlineBookExchange.service.MessageService;
import com.BookStore.OnlineBookExchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MessageController {

   private final MessageService messageService;
   private final UserService userService;


    @GetMapping("/message")
    public String sendMessagePage(
            @RequestParam Long listingId,
            Model model) {

        model.addAttribute("listingId", listingId);

        return "message";
    }

    @PostMapping("/message")
    public String sendMessage(
            @RequestParam Long listingId,
            @RequestParam String messageText) {

        messageService.sendMessage(listingId, messageText);

        return "redirect:/message?listingId=" + listingId;
    }

    @GetMapping("/message/inbox")
    public String messageInbox(Model model) {
        User user = userService.getCurrentUser();
        List<Message> messages= messageService.getInbox();
        model.addAttribute("messages", messages);
        model.addAttribute("userId", user.getId());
        return "message/inbox";
    }

    @GetMapping("/message/sent")
    public String messageSent(Model model) {
        List<Message> messages=messageService.getSent();
        model.addAttribute("messages",messages);
        return "message/sent";
    }

}
