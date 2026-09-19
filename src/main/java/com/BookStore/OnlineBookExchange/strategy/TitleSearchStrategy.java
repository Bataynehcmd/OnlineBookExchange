package com.BookStore.OnlineBookExchange.strategy;

import com.BookStore.OnlineBookExchange.entity.Listing;
import com.BookStore.OnlineBookExchange.repo.ListingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TitleSearchStrategy implements SearchStrategy {


    private final ListingRepo listingRepo;

    @Override
    public List<Listing> search(Object criteria) {
        String bookTitle = criteria.toString();

        return listingRepo.findByBookTitleContainingIgnoreCase(bookTitle);

    }

    @Override
    public String getType() {
        return "title";
    }

}
