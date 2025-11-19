package com.duoc.LevelUp.repositories;

import com.duoc.LevelUp.models.NoticiaImagen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticiaImagenRepository extends JpaRepository<NoticiaImagen,Long> {
    List<NoticiaImagen> findByNoticia_IdNoticia(Long idNoticia);
}
