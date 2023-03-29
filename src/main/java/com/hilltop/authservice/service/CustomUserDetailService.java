package com.hilltop.authservice.service;

import com.hilltop.authservice.config.CustomUserDetails;
import com.hilltop.authservice.entity.UserCredential;
import com.hilltop.authservice.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository userCredentialRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userOptional = userCredentialRepository.findByName(username);
        return userOptional.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

    }
}
