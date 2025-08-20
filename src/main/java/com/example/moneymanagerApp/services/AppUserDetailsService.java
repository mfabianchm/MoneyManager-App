package com.example.moneymanagerApp.services;

import com.example.moneymanagerApp.entities.ProfileEntity;
import com.example.moneymanagerApp.repositories.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom implementation of Spring Security's UserDetailsService.
 *
 * This service is used by Spring Security during authentication to load user details
 * from the database based on the provided email (username).
 *
 * - Retrieves a ProfileEntity using ProfileRepository.
 * - Throws UsernameNotFoundException if no user is found.
 * - Converts the ProfileEntity into a Spring Security UserDetails object,
 *   which contains the username, password, and authorities.
 *
 * This acts as a bridge between the application's ProfileEntity and
 * Spring Security's authentication mechanism.
 */


@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final ProfileRepository profileRepository;

//Spring calls this method automatically during authentication.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ProfileEntity existingProfile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Profile not found with email: "+ email));

        return User.builder()
                .username(existingProfile.getEmail())
                .password(existingProfile.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
