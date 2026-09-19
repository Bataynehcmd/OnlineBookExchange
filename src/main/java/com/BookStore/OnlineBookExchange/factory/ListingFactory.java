package com.BookStore.OnlineBookExchange.factory;

import com.BookStore.OnlineBookExchange.DTOs.CreateListingDTO;
import com.BookStore.OnlineBookExchange.entity.Listing;

public interface ListingFactory {

    Listing createListing(CreateListingDTO createListingDTO);
}
