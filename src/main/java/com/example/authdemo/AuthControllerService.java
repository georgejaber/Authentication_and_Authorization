package com.example.authdemo;

import com.example.authdemo.jwt.jwtUtilClass;
import com.example.authdemo.user.AuthUserDetailsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthControllerService {

    @Autowired
    jwtUtilClass jwt;
    @Autowired
    AuthUserDetailsService userDetailsService;

    @Autowired
    AuthenticationProvider provider;



    public AuthResponse login(@NotNull AuthRequest request) {
        provider.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String Token = jwt.generateToken(userDetails);

        return new AuthResponse(Token);
    }
}
