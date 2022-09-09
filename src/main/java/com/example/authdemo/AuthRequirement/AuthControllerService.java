package com.example.authdemo.AuthRequirement;

import com.example.authdemo.DTO.AuthRequest;
import com.example.authdemo.DTO.AuthResponse;
import com.example.authdemo.DTO.UserDTO;
import com.example.authdemo.JWT.jwtUtilClass;
import com.example.authdemo.User.AuthUserDetailsService;
import com.example.authdemo.User.User;
import com.example.authdemo.User.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
public class AuthControllerService {

    @Autowired
    jwtUtilClass jwt;
    @Autowired
    AuthUserDetailsService userDetailsService;

    @Autowired
    AuthenticationProvider provider;

    @Autowired
    UserRepository repository;


    PasswordEncoder encoder;


    public AuthResponse login(@NotNull AuthRequest request) {
        provider.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

        String Token = jwt.generateToken(userDetails);

        return new AuthResponse(Token);
    }

    public User register(@NotNull User user) throws Exception {
           String email =user.getEmail();
           if (email.isEmpty())
           {
               throw  new Exception("enter an email");
           }
           String pass=user.getPassword();

           encoder = new BCryptPasswordEncoder();

           user.setPassword(encoder.encode(pass));

           try {
               return repository.save(user);
           }
           catch (Exception e)
           {
               throw new Exception("email already exists");
           }
    }
}
