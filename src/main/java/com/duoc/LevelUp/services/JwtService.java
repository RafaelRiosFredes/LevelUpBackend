package com.duoc.LevelUp.services;

import com.duoc.LevelUp.models.Rol;
import com.duoc.LevelUp.models.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String SECRET_KEY = "clave-super-secreta-123";

    public String generateToken(Usuario user) {
        return Jwts.builder()
                .setSubject(user.getCorreo())
                .claim("id", user.getIdUsuario())
                .claim("roles", user.getRoles().stream().map(Rol::getNombreRol).toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}