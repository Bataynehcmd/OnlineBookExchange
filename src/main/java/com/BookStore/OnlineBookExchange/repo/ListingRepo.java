package com.BookStore.OnlineBookExchange.repo;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.BookStore.OnlineBookExchange.entity.Listing;
import com.BookStore.OnlineBookExchange.entity.ListingStatus;
import com.BookStore.OnlineBookExchange.entity.ListingType;
import com.BookStore.OnlineBookExchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepo extends JpaRepository<Listing, Long> {

    List<Listing> findByBookTitleContainingIgnoreCase(String bookTitle);

    List<Listing> findByCondition(String condition);

    List<Listing> findByListingType(ListingType type);

    List<Listing> findByCourseCode(Integer courseCode);

    List<Listing> findByCategory(String category);

    List<Listing> findByOwnerAndListingTypeAndListingStatus(
            User owner,
            ListingType listingType,
            ListingStatus listingStatus
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT l FROM Listing l WHERE l.listingId = :listingId")
    Optional<Listing> findByIdForUpdate(@Param("listingId") Long listingId);

    List<Listing> findByOwner(User owner);


}
