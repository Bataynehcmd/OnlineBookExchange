package com.BookStore.OnlineBookExchange.repo;

import com.BookStore.OnlineBookExchange.entity.Message;
import com.BookStore.OnlineBookExchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

    List<Message> findMessagesByReceiver(User receiver);

    List<Message> findMessagesBySender(User sender);

    void deleteByListing_ListingId(Long listingId);

}
