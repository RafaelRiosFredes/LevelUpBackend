package com.duoc.LevelUp.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Builder
public class NoticiaResponseDTO {
    private Long idNoticia;
    private String titulo;
    private String descripcion;
    private String autor;
    private String fechaPublicacion; // ya formateada "yyyy-MM-dd"
    private List<NoticiaImagenResponseDTO> imagenes;
}
