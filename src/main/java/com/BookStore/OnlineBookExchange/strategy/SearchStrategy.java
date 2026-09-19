package com.BookStore.OnlineBookExchange.strategy;

import com.BookStore.OnlineBookExchange.entity.Listing;

import java.util.List;

public interface SearchStrategy {

    List<Listing> search(Object criteria);

    String getType();
}
