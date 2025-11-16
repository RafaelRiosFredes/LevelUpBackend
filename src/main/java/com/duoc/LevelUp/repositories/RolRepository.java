package com.duoc.LevelUp.repositories;

import com.duoc.LevelUp.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long> {
    Optional<Rol> findByNombreRol(String nombreRol);
}
