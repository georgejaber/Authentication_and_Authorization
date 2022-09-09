package com.example.authdemo.AuthRequirement;

import com.example.authdemo.Filter.AuthFilter;
import com.example.authdemo.User.AuthUserDetailsService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration{

    AuthUserDetailsService service;

    public AuthConfiguration(AuthUserDetailsService service) {
        this.service = service;
    }

    @Bean
    AuthFilter filter(){
        return new AuthFilter();
    }

    @Bean
    public AuthenticationProvider authProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN","USER")
                .antMatchers("/").permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .and().logout().clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .and().httpBasic();
        return http.build();
    }


}
