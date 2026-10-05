package com.hybris.storefront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Hybris OOTB: Security configuration.
 * Mirrors SAP Commerce storefront security setup.
 *
 * Real Hybris uses:
 * - Spring Security for storefront (CSRF, XSS protection)
 * - OAuth2 / Spring Security OAuth for OCC API
 * - HybrisUserDetailsService backed by UserService
 * - RememberMe cookie with hybris token persistence
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/index", "/home", "/sale",
                    "/p/**", "/c/**",
                    "/products/**", "/categories/**",
                    "/search", "/search/**",
                    "/cart", "/cart/**",
                    "/checkout", "/checkout/**",
                    "/order/**", "/order-confirmation/**",
                    "/dashboard", "/dashboard/**",
                    "/analytics", "/analytics/**",
                    "/occ/**",
                    "/swagger-ui/**", "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/h2-console/**",
                    "/actuator/**",
                    "/css/**", "/js/**", "/images/**", "/static/**",
                    "/webjars/**", "/error"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
