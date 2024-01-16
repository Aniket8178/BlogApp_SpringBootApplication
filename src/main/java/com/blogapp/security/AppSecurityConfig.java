package com.blogapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration

public class AppSecurityConfig   {


	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
        .authorizeRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/h2-console/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/users", "/users/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/articles", "/articles/*").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/comments/**").permitAll()
            // Other authorization rules...
        )
        .csrf().disable()
		.headers().frameOptions().disable();
        return http.build();
    }
}
