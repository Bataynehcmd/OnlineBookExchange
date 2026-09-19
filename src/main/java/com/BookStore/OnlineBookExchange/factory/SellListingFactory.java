package com.BookStore.OnlineBookExchange.factory;

import com.BookStore.OnlineBookExchange.DTOs.CreateListingDTO;
import com.BookStore.OnlineBookExchange.entity.Listing;
import com.BookStore.OnlineBookExchange.entity.ListingType;
import org.springframework.stereotype.Component;

@Component
public class SellListingFactory implements ListingFactory{
    @Override
    public Listing createListing(CreateListingDTO createListingDTO) {
        Listing listing = new Listing();

        listing.setBookTitle(createListingDTO.bookTitle());
        listing.setAuthor(createListingDTO.author());
        listing.setEdition(createListingDTO.edition());
        listing.setCourseCode(createListingDTO.courseCode());
        listing.setCategory(createListingDTO.category());
        listing.setCondition(createListingDTO.condition());
        listing.setListingType(ListingType.SELL);
        listing.setPrice(createListingDTO.price());

        return listing;
    }
}
