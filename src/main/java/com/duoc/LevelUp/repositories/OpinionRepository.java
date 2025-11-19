package com.duoc.LevelUp.repositories;

import com.duoc.LevelUp.models.Opinion;
import com.duoc.LevelUp.models.OpinionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OpinionRepository extends JpaRepository<Opinion, OpinionId> {
    List<Opinion> findByProducto_IdProducto(Long idProducto);

    List<Opinion> findByUsuario_IdUsuario(Long idUsuario);

    Optional<Opinion> findByProducto_IdProductoAndUsuario_IdUsuario(Long idProducto, Long idUsuario);

    boolean existsByProducto_IdProductoAndUsuario_IdUsuario(Long idProducto, Long idUsuario);
}
