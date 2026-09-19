package com.BookStore.OnlineBookExchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Listing  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;

    @Version
    private Long version;

    private String bookTitle;

    private String author;

    private int edition;

    private int courseCode;

    private String category;

    @Column(name = "book_condition")
    private String condition;

    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    private ListingStatus listingStatus;

    @Enumerated(EnumType.STRING)
    private ListingType listingType;

    private double price;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Lob
    private byte[] image;

    private String imageType;
}
