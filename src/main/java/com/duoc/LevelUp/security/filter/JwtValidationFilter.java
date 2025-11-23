package com.duoc.LevelUp.security.filter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // Necesario para Collectors

import static com.duoc.LevelUp.security.TokenJwtConfig.*;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {

        String path = request.getServletPath();

        // RUTAS PÚBLICAS (NO VALIDAR JWT) - Esto ya maneja el POST /login
        if (path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars")
                || path.equals("/swagger-ui.html")
                || path.equals("/api/v1/auth/login")
                || path.equals("/api/v1/auth/registro")
                || path.startsWith("/api/v1/productos")
                || path.startsWith("/api/v1/categorias")
                || path.startsWith("/api/v1/noticias")) {

            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(JWT_TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(JWT_TOKEN_PREFIX, "");

        var claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token);

        String username = claims.getPayload().getSubject();

        // **INICIO DE LA CORRECCIÓN**
        String rolesJson = claims.getPayload().get("authorities").toString();

        // Paso 1: Deserializar el JSON que contiene los roles
        List<Map<String, String>> authoritiesMapList =
                new ObjectMapper().readValue(rolesJson, new TypeReference<>() {});

        // Paso 2: Mapear la lista de Maps a la clase concreta SimpleGrantedAuthority
        List<SimpleGrantedAuthority> authorities = authoritiesMapList.stream()
                .map(authMap -> new SimpleGrantedAuthority(authMap.get("authority")))
                .collect(Collectors.toList());
        // **FIN DE LA CORRECCIÓN**

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }
}