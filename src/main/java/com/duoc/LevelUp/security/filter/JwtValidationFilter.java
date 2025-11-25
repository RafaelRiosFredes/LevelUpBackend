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
import java.util.stream.Collectors;

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
        String method = request.getMethod();

        // 1) RUTAS PÚBLICAS

        if (path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars")
                || path.equals("/swagger-ui.html")
                || path.equals("/api/v1/auth/login")
                || path.equals("/api/v1/auth/registro")) {

            chain.doFilter(request, response);
            return;
        }


        // 2) GET públicos a productos/etc.

        if ("GET".equalsIgnoreCase(method) &&
                (path.startsWith("/api/v1/productos")
                        || path.startsWith("/api/v1/categorias")
                        || path.startsWith("/api/v1/noticias"))) {

            chain.doFilter(request, response);
            return;
        }


        // 3) Resto de rutas: mirar el header

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


        String rolesJson = claims.getPayload().get("authorities").toString();

        List<Map<String, String>> authoritiesMapList =
                new ObjectMapper().readValue(rolesJson, new TypeReference<>() {});

        List<SimpleGrantedAuthority> authorities = authoritiesMapList.stream()
                .map(authMap -> new SimpleGrantedAuthority(authMap.get("authority")))
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(username, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(auth);

        chain.doFilter(request, response);
    }
}
