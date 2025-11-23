package com.duoc.LevelUp.repositories;

import com.duoc.LevelUp.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // MÉTODO CORREGIDO para cargar el Usuario y sus Roles en una sola consulta
    // Esto previene el LazyInitializationException al acceder a los roles fuera de la sesión.
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.roles WHERE u.correo = :correo")
    Optional<Usuario> findByCorreoConRoles(@Param("correo") String correo);

    // Mantenemos este para uso general si se requiere sin roles
    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByCorreoIgnoreCase(String correo);

    boolean existsByCorreoIgnoreCase(String correo);
}