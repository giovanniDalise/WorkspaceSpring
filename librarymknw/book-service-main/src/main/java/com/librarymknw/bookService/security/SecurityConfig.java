package com.librarymknw.bookService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())  // Disabilitiamo CSRF (usato per form-based authentication)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API REST senza sessione
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/library").permitAll() // Tutti possono vedere i libri
                        .requestMatchers(HttpMethod.GET, "/library/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/library").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/library/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/library/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/library/findByBook").authenticated() // Solo autenticati possono cercare libri avanzati
                        .anyRequest().authenticated()
                )
                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))  // Configurazione CORS
                .addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

