package com.BookStore.OnlineBookExchange.service;


import com.BookStore.OnlineBookExchange.DTOs.CreateListingDTO;
import com.BookStore.OnlineBookExchange.DTOs.EditListingDTO;
import com.BookStore.OnlineBookExchange.entity.Listing;
import com.BookStore.OnlineBookExchange.entity.ListingStatus;
import com.BookStore.OnlineBookExchange.entity.ListingType;
import com.BookStore.OnlineBookExchange.entity.User;
import com.BookStore.OnlineBookExchange.factory.ExchangeListingFactory;
import com.BookStore.OnlineBookExchange.factory.ListingFactory;
import com.BookStore.OnlineBookExchange.factory.SellListingFactory;
import com.BookStore.OnlineBookExchange.repo.ListingRepo;
import com.BookStore.OnlineBookExchange.repo.MessageRepo;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import com.BookStore.OnlineBookExchange.strategy.SearchStrategy;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final UserService userService;
    private final ListingRepo listingRepo;
    private final MessageRepo messageRepo;

    private final SellListingFactory sellListingFactory;
    private final ExchangeListingFactory exchangeListingFactory;


    public void createListing(CreateListingDTO createListingDTO) {

        ListingFactory listingFactory;

        if (createListingDTO.listingType() == ListingType.SELL) {
            listingFactory = sellListingFactory;
        } else {
            listingFactory = exchangeListingFactory;
        }

        Listing listing = listingFactory.createListing(createListingDTO);

        try {
            if (createListingDTO.image() != null &&
                    !createListingDTO.image().isEmpty()
            ) {
                if (createListingDTO.image().getSize() > 5 * 1024 * 1024) {
                    throw new IllegalArgumentException("Image size must not exceed 5MB");
                }
                listing.setImage(createListingDTO.image().getBytes());
                listing.setImageType(createListingDTO.image().getContentType());
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to update image", e);
        }

        if (createListingDTO.listingType() == ListingType.SELL) {

            if (createListingDTO.price() == null ||
                    createListingDTO.price() <= 0) {

                throw new IllegalArgumentException(
                        "Price must be greater than 0 for sell listings"
                );
            }
        }

        listing.setOwner(userService.getCurrentUser());
        listing.setListingStatus(ListingStatus.AVAILABLE);
        listing.setExpiryDate(LocalDate.now().plusDays(30));

        listingRepo.save(listing);
    }

    public List<Listing> getAllListings() {
        return listingRepo.findAll();
    }

    private final List<SearchStrategy> searchStrategies;

    public List<Listing> getListingsFilter(String type, Object criteria) {

        SearchStrategy strategy = searchStrategies.stream()
                .filter(s -> s.getType().equals(type))
                .findFirst()
                .orElseThrow();
        return strategy.search(criteria);

    }

    @Transactional
    public void reserveListing(Long listingId) {

        User currentUser = userService.getCurrentUser();

        Listing listing = listingRepo.findById(listingId)
                .orElseThrow();

        // Owner cannot reserve his own listing
        if (currentUser.getId().equals(listing.getOwner().getId())) {
            throw new IllegalStateException(
                    "You cannot reserve your own listing"
            );
        }

        if (listing.getListingStatus() == ListingStatus.AVAILABLE
                && listing.getListingType() == ListingType.SELL) {
            try {
                listing.setListingStatus(ListingStatus.RESERVED);
                listingRepo.save(listing);
            } catch (ObjectOptimisticLockingFailureException e) {
                throw new IllegalStateException(
                        "This listing was already reserved by another user"
                );
            }

        }
    }

    public void cancelReservation(Long listingId) {

        User currentUser = userService.getCurrentUser();

        Listing listing = listingRepo.findById(listingId)
                .orElseThrow();

        if (!currentUser.getId().equals(listing.getOwner().getId())) {
            throw new IllegalStateException(
                    "Only the owner can cancel this reservation"
            );
        }

        if (listing.getListingStatus() == ListingStatus.RESERVED
                && listing.getListingType() == ListingType.SELL) {

            listing.setListingStatus(ListingStatus.AVAILABLE);
            listingRepo.save(listing);
        }
    }

    public void completeSale(Long listingId) {

        User currentUser = userService.getCurrentUser();

        Listing listing = listingRepo.findById(listingId)
                .orElseThrow();

        // Only the owner can complete the sale
        if (!currentUser.getId().equals(listing.getOwner().getId())) {
            throw new IllegalStateException(
                    "Only the owner can complete this sale"
            );
        }

        if (listing.getListingStatus() == ListingStatus.RESERVED
                && listing.getListingType() == ListingType.SELL) {

            listing.setListingStatus(ListingStatus.SOLD);
            listingRepo.save(listing);
        }
    }

    public List<Listing> getMyAvailableExchangeListings() {
        User currentUser = userService.getCurrentUser();
        return listingRepo.findByOwnerAndListingTypeAndListingStatus(
                currentUser,
                ListingType.EXCHANGE,
                ListingStatus.AVAILABLE
        );

    }

    public Listing getListingById(Long listingId) {
        return listingRepo.findById(listingId).orElseThrow();
    }

    public List<Listing> getMyListings() {
        User currentUser = userService.getCurrentUser();

        return listingRepo.findByOwner(currentUser);

    }

    public Listing getMyListingById(Long listingId) {
        User currentUser = userService.getCurrentUser();

        Listing listing = listingRepo.findById(listingId)
                .orElseThrow();

        if (currentUser.getId().equals(listing.getOwner().getId())
                && listing.getListingStatus() == ListingStatus.AVAILABLE) {

            return listing;
        } else {
            throw new IllegalStateException("You cannot edit this listing");
        }

    }

    public void updateListing(Long listingId, EditListingDTO editListingDTO) {
        User currentUser = userService.getCurrentUser();
        Listing listing =
                listingRepo.findById(listingId).orElseThrow();
        if (currentUser.getId().equals(listing.getOwner().getId())
                && listing.getListingStatus() == ListingStatus.AVAILABLE) {
            listing.setBookTitle(editListingDTO.bookTitle());
            listing.setAuthor(editListingDTO.author());
            listing.setEdition(editListingDTO.edition());
            listing.setCourseCode(editListingDTO.courseCode());
            listing.setCategory(editListingDTO.category());
            listing.setCondition(editListingDTO.condition());
            listing.setPrice(editListingDTO.price());
            listingRepo.save(listing);
        } else {
            throw new IllegalStateException("You cannot update this listing");
        }
    }

    @Transactional
    public void deleteListing(Long listingId) {
        User currentUser = userService.getCurrentUser();
        Listing listing =
                listingRepo.findById(listingId).orElseThrow();
        if (currentUser.getId().equals(listing.getOwner().getId())
                && listing.getListingStatus() == ListingStatus.AVAILABLE) {
            messageRepo.deleteByListing_ListingId(listingId);
            listingRepo.delete(listing);
        } else {
            throw new IllegalStateException("You cannot delete this listing");
        }
    }

    @Transactional
    public void deleteListingByAdmin(Long listingId) {
        Listing listing = listingRepo.findById(listingId).orElseThrow();
        messageRepo.deleteByListing_ListingId(listingId);
        listingRepo.delete(listing);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredListings() {
        List<Listing> listings = listingRepo.findAll();

        for (Listing listing : listings) {
            if (listing.getExpiryDate() != null
                    && listing.getExpiryDate().isBefore(LocalDate.now())) {

                messageRepo.deleteByListing_ListingId(listing.getListingId());
                listingRepo.delete(listing);
            }
        }
    }


}


