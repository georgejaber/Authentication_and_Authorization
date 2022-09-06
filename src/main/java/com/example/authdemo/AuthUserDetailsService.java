package com.example.authdemo;

import com.example.authdemo.user.User;
import com.example.authdemo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repository;
    public AuthUserDetailsService() {

    }

    @Override
    public UserDetails loadUserByUsername(String email){

        User user = repository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("no user found with the given email" + email);
        }
        return new AuthUserDetails(user);
    }

}
