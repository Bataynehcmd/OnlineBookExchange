package com.BookStore.OnlineBookExchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeProposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long proposalId;

    @ManyToOne
    @JoinColumn(name = "proposer_id")
    User proposer;

    @ManyToOne
    @JoinColumn(name = "reciever_id")
    User receiver;

    @ManyToOne
    @JoinColumn(name = "requestedListing_id")
    Listing requestedListing;

    @ManyToOne
    @JoinColumn(name = "offeredListing_id")
    Listing offeredListing;

    Proposal proposal;

    boolean proposerConfirmed;

    boolean receiverConfirmed;
}
