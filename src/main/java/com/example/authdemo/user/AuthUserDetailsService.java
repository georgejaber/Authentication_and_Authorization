package com.example.authdemo.user;

import com.example.authdemo.user.AuthUserDetails;
import com.example.authdemo.user.User;
import com.example.authdemo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repository;
    public AuthUserDetailsService() {}

    @Override
    public UserDetails loadUserByUsername(String email){

        User user = repository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("no user found with the given email" + email);
        }
        return new AuthUserDetails(user);
    }

}
