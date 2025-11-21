package com.duoc.LevelUp.security;

import com.duoc.LevelUp.security.filter.JwtAuthenticationFilter;
import com.duoc.LevelUp.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationFilter authFilter = new JwtAuthenticationFilter(authenticationManager());
        authFilter.setFilterProcessesUrl("/api/v1/auth/login");

        return http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // ✔ Swagger público
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // Login y registro público
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/registro").permitAll()

                        // Productos públicos
                        .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").permitAll()

                        // Categorías públicas
                        .requestMatchers(HttpMethod.GET, "/api/v1/categorias/**").permitAll()

                        // Noticias públicas
                        .requestMatchers(HttpMethod.GET, "/api/v1/noticias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/noticias/imagenes/**").permitAll()

                        // ADMIN - Productos
                        .requestMatchers(HttpMethod.POST, "/api/v1/productos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/productos/**").hasRole("ADMIN")

                        // ADMIN - Noticias
                        .requestMatchers(HttpMethod.POST, "/api/v1/noticias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/noticias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/noticias/**").hasRole("ADMIN")

                        // ADMIN - Categorías
                        .requestMatchers(HttpMethod.POST, "/api/v1/categorias").hasRole("ADMIN")


                        .requestMatchers("/api/v1/productos/*/opiniones/**").authenticated()


                        .anyRequest().authenticated()
                )


                .addFilter(authFilter)
                .addFilter(new JwtValidationFilter(authenticationManager()))

                .build();
    }
}
