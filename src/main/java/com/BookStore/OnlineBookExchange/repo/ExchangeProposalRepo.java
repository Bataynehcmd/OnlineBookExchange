package com.BookStore.OnlineBookExchange.repo;

import com.BookStore.OnlineBookExchange.entity.ExchangeProposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeProposalRepo extends JpaRepository<ExchangeProposal, Long> {

    List<ExchangeProposal> findByRequestedListing_ListingId(Long requestedListingId);

}
