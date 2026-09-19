package com.BookStore.OnlineBookExchange.controller;

import com.BookStore.OnlineBookExchange.service.ExchangeProposalService;
import com.BookStore.OnlineBookExchange.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ExchangeProposalController {

    private final ExchangeProposalService exchangeProposalService;
    private final ListingService listingService;


    @PostMapping("/proposals")
    public String createProposal(
            @RequestParam Long requestedListingId,
            @RequestParam Long offeredListingId) {

        exchangeProposalService.createProposal(
                requestedListingId,
                offeredListingId
        );

        return "redirect:/listings/" + requestedListingId + "/proposals";
    }

    @GetMapping("/listings/{listingId}/proposals")
    public String getProposals(@PathVariable Long listingId, Model model) {
        model.addAttribute("proposals",
                exchangeProposalService.getProposals(listingId));

        return "proposals";
    }

    @GetMapping("/proposals/create")
    public String makeProposal(
            @RequestParam Long requestedListingId,
            Model model) {

        model.addAttribute(
                "myListings",
                listingService.getMyAvailableExchangeListings()
        );

        model.addAttribute("requestedListingId", requestedListingId);

        return "create-proposal";
    }

    @PostMapping("/proposals/{proposalId}/reject")
    public String rejectProposal(@PathVariable Long proposalId) {
        exchangeProposalService.rejectProposal(proposalId);
        return "redirect:/listings/all";
    }

    @PostMapping("/proposals/{proposalId}/accept")
    public String acceptProposal(@PathVariable Long proposalId) {
        exchangeProposalService.acceptProposal(proposalId);
        return "redirect:/listings/all";

    }

    @PostMapping("/proposals/{proposalId}/confirm")
    public String confirmExchange(@PathVariable Long proposalId){
        exchangeProposalService.confirmExchange(proposalId);
        return "redirect:/listings/all";
    }

    @PostMapping("/proposals/{proposalId}/cancel")
    public String cancelExchange(@PathVariable Long proposalId) {
        exchangeProposalService.cancelExchange(proposalId);
        return "redirect:/listings/all";
    }

}
