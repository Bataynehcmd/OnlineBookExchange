package com.BookStore.OnlineBookExchange.strategy;

import com.BookStore.OnlineBookExchange.entity.Listing;
import com.BookStore.OnlineBookExchange.repo.ListingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseCodeSearchStrategy implements SearchStrategy {
    private final ListingRepo listingRepo;

    @Override
    public List<Listing> search(Object criteria) {
        Integer courseCode = Integer.parseInt(criteria.toString());

        return listingRepo.findByCourseCode(courseCode);
    }

    @Override
    public String getType() {
        return "courseCode";
    }
}
