package com.BookStore.OnlineBookExchange.service;

import com.BookStore.OnlineBookExchange.entity.*;
import com.BookStore.OnlineBookExchange.excption.ResourceNotFoundException;
import com.BookStore.OnlineBookExchange.repo.ExchangeProposalRepo;
import com.BookStore.OnlineBookExchange.repo.ListingRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ExchangeProposalService {
    private final ExchangeProposalRepo repo;
    private final ListingRepo listingRepo;
    private final UserService userService;


    public void createProposal(Long requestedListingId, Long offeredListingId) {

        Listing requestedListing = listingRepo.findById(requestedListingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Requested listing not found"));

        Listing offeredListing = listingRepo.findById(offeredListingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Offered listing not found"));

        if (requestedListing.getOwner().getId().equals(userService.getCurrentUser().getId())) {
            throw new IllegalStateException("You cannot make a proposal on your own listing");
        }

        if (!offeredListing.getOwner().getId().equals(userService.getCurrentUser().getId())) {
            throw new IllegalStateException("You can only offer your own listing");
        }

        if (requestedListing.getListingId().equals(offeredListing.getListingId())) {
            throw new IllegalStateException("You cannot offer the same listing");
        }

        if (requestedListing.getListingType() != ListingType.EXCHANGE ||
                requestedListing.getListingStatus() != ListingStatus.AVAILABLE) {
            throw new IllegalStateException("Requested listing is not available for exchange");
        }
        if (offeredListing.getListingType() != ListingType.EXCHANGE ||
                offeredListing.getListingStatus() != ListingStatus.AVAILABLE) {
            throw new IllegalStateException("Offered listing is not available for exchange");
        }

        ExchangeProposal proposal = new ExchangeProposal();

        proposal.setProposer(userService.getCurrentUser());
        proposal.setReceiver(requestedListing.getOwner());
        proposal.setRequestedListing(requestedListing);
        proposal.setOfferedListing(offeredListing);
        proposal.setProposal(Proposal.PENDING);

        repo.save(proposal);
    }

    public List<ExchangeProposal> getProposals(Long requestedListingId) {

        return repo.findByRequestedListing_ListingId(requestedListingId);
    }

    public void rejectProposal(Long proposalId) {

        User currentUser = userService.getCurrentUser();

        ExchangeProposal proposal = repo.findById(proposalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Proposal not found"));

        if (!currentUser.getId().equals(proposal.getReceiver().getId())) {
            throw new IllegalStateException("You are not allowed to reject this proposal");
        }

        if (proposal.getProposal() == Proposal.PENDING) {
            proposal.setProposal(Proposal.REJECTED);
            repo.save(proposal);
        }
    }

    @Transactional
    public void acceptProposal(Long proposalId) {

        User currentUser = userService.getCurrentUser();

        ExchangeProposal proposal = repo.findById(proposalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Proposal not found"));

        if (!currentUser.getId().equals(proposal.getReceiver().getId())) {
            throw new IllegalStateException("You are not allowed to accept this proposal");
        }

        if (proposal.getProposal() == Proposal.PENDING) {

            Listing requestedListing =
                    listingRepo.findByIdForUpdate(
                            proposal.getRequestedListing().getListingId()
                    ).orElseThrow();

            Listing offeredListing =
                    listingRepo.findByIdForUpdate(
                            proposal.getOfferedListing().getListingId()
                    ).orElseThrow();

            if (requestedListing.getListingStatus() != ListingStatus.AVAILABLE
                    || offeredListing.getListingStatus() != ListingStatus.AVAILABLE) {

                throw new IllegalStateException(
                        "One of the listings is no longer available"
                );
            }
            proposal.setProposal(Proposal.ACCEPTED);
            requestedListing.setListingStatus(ListingStatus.RESERVED);
            offeredListing.setListingStatus(ListingStatus.RESERVED);

            listingRepo.save(offeredListing);
            listingRepo.save(requestedListing);
            repo.save(proposal);
        }
    }

    public void confirmExchange(Long proposalId) {

        User currentUser = userService.getCurrentUser();

        ExchangeProposal proposal = repo.findById(proposalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Proposal not found"));

        if (!currentUser.getId().equals(proposal.getProposer().getId())
                && !currentUser.getId().equals(proposal.getReceiver().getId())) {

            throw new IllegalStateException(
                    "You are not allowed to confirm this exchange"
            );
        }

        Listing requestedListing = proposal.getRequestedListing();
        Listing offeredListing = proposal.getOfferedListing();

        if (proposal.getProposal() == Proposal.ACCEPTED) {

            if (currentUser.getId().equals(proposal.getProposer().getId())) {
                proposal.setProposerConfirmed(true);
            }

            if (currentUser.getId().equals(proposal.getReceiver().getId())) {
                proposal.setReceiverConfirmed(true);
            }

            if (proposal.isProposerConfirmed()
                    && proposal.isReceiverConfirmed()) {

                requestedListing.setListingStatus(ListingStatus.EXCHANGED);
                offeredListing.setListingStatus(ListingStatus.EXCHANGED);

                requestedListing.setOwner(proposal.getProposer());
                offeredListing.setOwner(proposal.getReceiver());

                listingRepo.save(requestedListing);
                listingRepo.save(offeredListing);
            }

            repo.save(proposal);
        }
    }
    public void cancelExchange(Long proposalId) {

        User currentUser = userService.getCurrentUser();

        ExchangeProposal proposal = repo.findById(proposalId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Proposal not found"));

        // Only proposer or receiver can cancel
        if (!currentUser.getId().equals(proposal.getProposer().getId())
                && !currentUser.getId().equals(proposal.getReceiver().getId())) {

            throw new IllegalStateException(
                    "You are not allowed to cancel this exchange"
            );
        }

        if (proposal.getProposal() == Proposal.ACCEPTED) {

            Listing requestedListing = proposal.getRequestedListing();
            Listing offeredListing = proposal.getOfferedListing();

            requestedListing.setListingStatus(ListingStatus.AVAILABLE);
            offeredListing.setListingStatus(ListingStatus.AVAILABLE);

            proposal.setProposal(Proposal.CANCELLED);

            listingRepo.save(requestedListing);
            listingRepo.save(offeredListing);
            repo.save(proposal);
        }
    }

}
