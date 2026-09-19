package com.BookStore.OnlineBookExchange.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Please enter a valid email")
        String email,

        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Password cannot be blank")
        String password,

        @NotNull(message = "The Year cannot be blank")
        Integer year
) {
}
