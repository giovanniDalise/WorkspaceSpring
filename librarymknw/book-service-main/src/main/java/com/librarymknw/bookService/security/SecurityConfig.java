/*package com.librarymknw.bookService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())  // Disabilita CSRF solo se necessario
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new CorsFilter(corsConfigurationSource), UsernamePasswordAuthenticationFilter.class) // Aggiunge il filtro CORS
                .build();
    }
}

/*
package com.librarymknw.bookService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disabilita CSRF se non è necessario
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/library/**").permitAll()  // Permetti GET senza autenticazione
                        .requestMatchers(HttpMethod.POST, "/library/**").authenticated()  // Solo per utenti autenticati
                        .requestMatchers(HttpMethod.PUT, "/library/**").authenticated()  // Solo per utenti autenticati
                        .requestMatchers(HttpMethod.DELETE, "/library/**").authenticated()  // Solo per utenti autenticati
                        .anyRequest().authenticated()  // Tutte le altre richieste richiedono autenticazione
                )
                .addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)  // Aggiungi il filtro JWT
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));  // Configura CORS

        return http.build();
    }

    // Configurazione CORS
    private UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");  // Consenti tutte le origini (modificare se necessario)
        config.addAllowedMethod(HttpMethod.GET);  // Permetti il metodo GET
        config.addAllowedMethod(HttpMethod.POST);  // Permetti il metodo POST
        config.addAllowedMethod(HttpMethod.PUT);  // Permetti il metodo PUT
        config.addAllowedMethod(HttpMethod.DELETE);  // Permetti il metodo DELETE
        config.addAllowedHeader("*");  // Consenti qualsiasi header
        source.registerCorsConfiguration("/**", config);  // Applica la configurazione CORS a tutte le rotte
        return source;
    }
}
*/
