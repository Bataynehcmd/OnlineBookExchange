package com.BookStore.OnlineBookExchange.service;

import com.BookStore.OnlineBookExchange.entity.Listing;
import com.BookStore.OnlineBookExchange.entity.Message;
import com.BookStore.OnlineBookExchange.entity.User;
import com.BookStore.OnlineBookExchange.repo.MessageRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final UserService userService;
    private final MessageRepo messageRepo;
    private final ListingService listingService;

    public void sendMessage(Long listingId, String messageText) {

        Message message = new Message();

        User sender = userService.getCurrentUser();
        Listing listing = listingService.getListingById(listingId);
        User receiver = listing.getOwner();

        LocalDateTime now = LocalDateTime.now();

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setListing(listing);
        message.setMessageText(messageText);
        message.setDateTime(now);

        messageRepo.save(message);
    }

    public List<Message> getInbox() {

        User user = userService.getCurrentUser();
        List<Message> message = messageRepo.findMessagesByReceiver(user);
        return message;
    }

    public List<Message> getSent() {
        User user = userService.getCurrentUser();
        List<Message> message = messageRepo.findMessagesBySender(user);
        return message;

    }





}
