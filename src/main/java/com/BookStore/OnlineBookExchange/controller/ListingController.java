package com.BookStore.OnlineBookExchange.controller;

import com.BookStore.OnlineBookExchange.DTOs.CreateListingDTO;
import com.BookStore.OnlineBookExchange.DTOs.EditListingDTO;
import com.BookStore.OnlineBookExchange.entity.Listing;
import com.BookStore.OnlineBookExchange.entity.ListingType;
import com.BookStore.OnlineBookExchange.service.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;


    @GetMapping("/listings")
    public String createListingPage() {
        return "create-listing";
    }

    @PostMapping("/listings")
    public String createListing(
            @Valid @ModelAttribute CreateListingDTO createListingDTO,
            Model model) {
        try {
            listingService.createListing(createListingDTO);
            return "redirect:/listings/all";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "create-listing";
        }

    }

    @GetMapping("/listings/{listingId}/image")
    @ResponseBody
    public ResponseEntity<byte[]> getListingImage(
            @PathVariable Long listingId) {

        Listing listing = listingService.getListingById(listingId);

        if (listing.getImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, listing.getImageType())
                .body(listing.getImage());
    }

    @GetMapping("/listings/all")
    public String allListings(Model model) {
        model.addAttribute("listings", listingService.getAllListings());
        return "listings";
    }

    @GetMapping("/listings/search")
    public String searchListings(@RequestParam String bookTitle, Model model) {
        model.addAttribute("listings", listingService.getListingsFilter("title", bookTitle));
        return "listings";
    }

    @GetMapping("/listings/filter/condition")
    public String filterConditionListings(@RequestParam String condition, Model model) {
        model.addAttribute("listings", listingService.getListingsFilter("condition", condition));
        return "listings";
    }

    @GetMapping("/listings/filter/type")
    public String filterTypeListings(@RequestParam ListingType type, Model model) {
        model.addAttribute("listings", listingService.getListingsFilter("type", type));
        return "listings";
    }

    @GetMapping("/listings/filter/code")
    public String filterCourseCodeListings(
            @RequestParam(required = false) Integer courseCode,
            Model model) {

        if (courseCode == null) {
            model.addAttribute("listings", listingService.getAllListings());
        } else {
            model.addAttribute("listings",
                    listingService.getListingsFilter("courseCode", courseCode));
        }

        return "listings";
    }

    @GetMapping("/listings/filter/category")
    public String filterCategoryListings(@RequestParam String category, Model model) {
        model.addAttribute("listings", listingService.getListingsFilter("category", category));
        return "listings";
    }

    @PostMapping("/listings/{listingId}/reserve")
    public String reserveListing(@PathVariable Long listingId) {
        listingService.reserveListing(listingId);
        return "redirect:/listings/all";
    }

    @PostMapping("/listings/{listingId}/cancel")
    public String cancelReservation(@PathVariable Long listingId) {
        listingService.cancelReservation(listingId);
        return "redirect:/listings/all";
    }

    @PostMapping("/listings/{listingId}/complete")
    public String completeSale(@PathVariable Long listingId) {
        listingService.completeSale(listingId);
        return "redirect:/listings/all";
    }

    @GetMapping("/listings/my-listings")
    public String myListings(Model model) {
        model.addAttribute("listings", listingService.getMyListings());
        return "listings/my-listings";
    }

    @GetMapping("/listings/{listingId}/edit-listing")
    public String editListing(@PathVariable Long listingId, Model model) {
        model.addAttribute("listing", listingService.getMyListingById(listingId));
        return "listings/edit-listing";
    }

    @PostMapping("/listings/{listingId}/edit")
    public String updateListing(
            @PathVariable Long listingId,
            @Valid @ModelAttribute EditListingDTO editListingDTO) {

        listingService.updateListing(listingId, editListingDTO);

        return "redirect:/listings/my-listings";
    }

    @PostMapping("/listings/{listingId}/delete")
    public String deleteListing(@PathVariable Long listingId) {
        listingService.deleteListing(listingId);
        return "redirect:/listings/my-listings";
    }

}
