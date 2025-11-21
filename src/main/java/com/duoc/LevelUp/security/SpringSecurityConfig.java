package com.duoc.LevelUp.security;



import com.duoc.LevelUp.security.filter.JwtAuthenticationFilter;

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
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder();  }

    @Bean
    AuthenticationManager authenticationManager() throws Exception{
        return this.authenticationConfiguration.getAuthenticationManager();
    }

    // Componente que gestiona los accesos de cada endpoint
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authz) -> {
            authz   .requestMatchers(HttpMethod.GET,"/api/v1/productos","/api/v1/productos/{id}").permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/v1/usuarios").permitAll()

                    .requestMatchers(HttpMethod.POST,"/api/v1/productos").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT,"/api/v1/productos/{id}").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE,"/api/v1/productos{id}").hasRole("ADMIN")
                    .anyRequest().authenticated();
        })
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtVa)

    }
}