package com.ing.brokerage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Allow access to H2 console
                        .requestMatchers("/h2-console/**").permitAll()
                        // Login endpoint for customers (public)
                        .requestMatchers("/customers/login").permitAll()
                        // Admin endpoints require ADMIN role
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Other endpoints require authentication (customer or admin)
                        .anyRequest().authenticated()
                )
                .httpBasic()
                .and()
                .csrf().disable() // Disable CSRF for simplicity in this demo
                .headers().frameOptions().disable(); // Allow H2 console to be displayed in a frame
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // In-memory admin user for demonstration
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build()
        );
        return manager;
    }
}