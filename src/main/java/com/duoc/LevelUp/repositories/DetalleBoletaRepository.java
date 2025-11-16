package com.duoc.LevelUp.repositories;

import com.duoc.LevelUp.models.DetalleBoleta;
import com.duoc.LevelUp.models.DetalleBoletaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, DetalleBoletaId> {
}
