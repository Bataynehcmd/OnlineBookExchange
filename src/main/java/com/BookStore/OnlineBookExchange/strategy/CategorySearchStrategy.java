package com.BookStore.OnlineBookExchange.strategy;

import com.BookStore.OnlineBookExchange.entity.Listing;
import com.BookStore.OnlineBookExchange.repo.ListingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategorySearchStrategy implements SearchStrategy {

    private final ListingRepo listingRepo;

    @Override
    public List<Listing> search(Object criteria) {
        String category = criteria.toString();

        return listingRepo.findByCategory(category);

    }

    @Override
    public String getType() {
        return "category";
    }
}
