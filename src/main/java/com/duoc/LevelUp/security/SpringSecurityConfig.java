package com.duoc.LevelUp.security;

import com.duoc.LevelUp.security.filter.JwtAuthenticationFilter;
import com.duoc.LevelUp.security.filter.JwtValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

    // ----------------------------------------------------
    // 1. BEANS BÁSICOS
    // ----------------------------------------------------
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // ----------------------------------------------------
    // 2. CONFIGURACIÓN DE LA CADENA DE FILTROS (SecurityFilterChain)
    // Se inyecta AuthenticationManager y se crean los filtros localmente.
    // ----------------------------------------------------
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        // 1. Crear e Inicializar JwtAuthenticationFilter (LOGIN)
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager);

        // CRUCIAL: Aseguramos la URL correcta del login.
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/v1/auth/login");

        // 2. Crear e Inicializar JwtValidationFilter (VALIDACIÓN)
        JwtValidationFilter jwtValidationFilter = new JwtValidationFilter(authenticationManager);

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth

                        // Rutas públicas (Swagger)
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // Auth público (Login y Registro)
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/registro").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Productos públicos
                        .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/categorias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/noticias/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/noticias/imagenes/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/boletas").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v1/usuarios/**").authenticated()
                        // Admin
                        .requestMatchers(HttpMethod.POST, "/api/v1/productos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/productos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/noticias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/noticias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/noticias/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/categorias").hasRole("ADMIN")



                        .requestMatchers("/api/v1/productos/*/opiniones/**").authenticated()

                        .anyRequest().authenticated()
                )

                // IMPORTANTE: Añadir primero el filtro de autenticación (Login)
                .addFilter(jwtAuthenticationFilter)

                // Luego el filtro de validación (JWT Check)
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    // ----------------------------------------------------
    // 3. CORS Configuration
    // ----------------------------------------------------
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:3000"
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}