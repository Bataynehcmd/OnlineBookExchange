package com.BookStore.OnlineBookExchange.strategy;


import com.BookStore.OnlineBookExchange.entity.Listing;
import com.BookStore.OnlineBookExchange.entity.ListingType;
import com.BookStore.OnlineBookExchange.repo.ListingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TypeSearchStrategy implements SearchStrategy {

    private final ListingRepo listingRepo;

    @Override
    public List<Listing> search(Object criteria) {
        ListingType type = ListingType.valueOf(criteria.toString());

        return listingRepo.findByListingType(type);

    }

    @Override
    public String getType() {
        return "type";
    }
}
