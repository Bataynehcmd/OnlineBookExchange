package com.BookStore.OnlineBookExchange.service;

import com.BookStore.OnlineBookExchange.DTOs.UpdateProfileDTO;
import com.BookStore.OnlineBookExchange.DTOs.UserDTO;
import com.BookStore.OnlineBookExchange.entity.User;
import com.BookStore.OnlineBookExchange.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User register(UserDTO userDTO) {

        User user = new User();
        user.setEmail(userDTO.email());
        user.setUsername(userDTO.username());
        user.setYear(userDTO.year());
        user.setPassword(passwordEncoder.encode(userDTO.password()));

        user.setRoles(List.of("USER"));

        return userRepo.save(user);
    }

    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return userRepo.findByUsername(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username Not Found"));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Username Not Found"));
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void toggleBlockUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        user.setBlocked(!user.isBlocked());

        userRepo.save(user);
    }

    public void updateProfile(UpdateProfileDTO dto) {
        User currentUser = getCurrentUser();

        if (!currentUser.getEmail().equals(dto.email())
                && userRepo.findByEmail(dto.email()).isPresent()) {
            throw new IllegalStateException("Email is already in use");
        }

        currentUser.setEmail(dto.email());
        currentUser.setYear(dto.year());

        userRepo.save(currentUser);
    }



}

