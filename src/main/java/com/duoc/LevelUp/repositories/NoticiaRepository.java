package com.duoc.LevelUp.repositories;

import com.duoc.LevelUp.models.Noticia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {
}
