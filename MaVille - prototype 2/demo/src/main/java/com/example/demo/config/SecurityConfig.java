package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuration de la sécurité Spring Security pour l'application.
 * Gère la configuration CORS, CSRF et les règles d'autorisation des requêtes.
 */
@Configuration
public class SecurityConfig {

    /**
     * Définit la chaîne de filtres de sécurité pour gérer les requêtes HTTP.
     *
     * @param http L'objet HttpSecurity pour configurer la sécurité.
     * @return La chaîne de filtres de sécurité configurée.
     * @throws Exception En cas d'erreur dans la configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Configuration CORS
            .csrf(csrf -> csrf.disable())  // Désactive la protection CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/api/**", "/api/message").permitAll()  // Permet l'accès sans authentification à certains endpoints
                .anyRequest().authenticated()  // Requiert une authentification pour toutes les autres requêtes
            );

        return http.build();
    }

    /**
     * Définit la configuration CORS pour l'application.
     *
     * @return La source de configuration CORS.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // Origines autorisées
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Méthodes autorisées
        configuration.setAllowedHeaders(List.of("*"));  // En-têtes autorisés
        configuration.setAllowCredentials(true);  // Permet l'utilisation des cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Applique la configuration CORS à tous les endpoints
        return source;
    }
}
