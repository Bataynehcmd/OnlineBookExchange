package com.BookStore.OnlineBookExchange.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EditListingDTO(


        @NotBlank(message = "Book Title cannot be blank")
        String bookTitle,

        @NotBlank(message = "Author cannot be blank")
        String author,

        @NotNull(message = "Edition cannot be blank")
        Integer edition,

        @NotNull(message = "Course Code cannot be blank")
        Integer courseCode,

        @NotBlank(message = "Category cannot be blank")
        String category,

        @NotBlank(message = "Condition cannot be blank")
        String condition,

        Double price

) {
}
