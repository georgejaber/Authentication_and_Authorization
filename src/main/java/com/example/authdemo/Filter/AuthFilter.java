package com.example.authdemo.Filter;

import com.example.authdemo.JWT.jwtUtilClass;
import com.example.authdemo.User.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    AuthUserDetailsService service;

    @Autowired
    jwtUtilClass jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");
        final SecurityContext context = SecurityContextHolder.getContext();
        String email="";

        if(header!=null && header.startsWith("Bearer ")&& context.getAuthentication()==null)
        {
            String token = header.substring("Bearer ".length());
            email = jwt.getUsernameFromToken(token);
            if(!email.isEmpty()) {
                UserDetails details = service.loadUserByUsername(email);
                if (jwt.validateToken(token,details))
                {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            details,null,details.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                    context.setAuthentication(authenticationToken);
                }
            }

        }
        filterChain.doFilter(request,response);
    }
}
