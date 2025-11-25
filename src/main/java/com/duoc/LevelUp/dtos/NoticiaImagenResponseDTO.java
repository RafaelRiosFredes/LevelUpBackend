package com.duoc.LevelUp.dtos;

import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor @Builder
public class NoticiaImagenResponseDTO {

    private Long idImagenNoticia;
    private String url;
    private String contentType;
    private Long sizeBytes;
    private String nombreArchivo;
}
