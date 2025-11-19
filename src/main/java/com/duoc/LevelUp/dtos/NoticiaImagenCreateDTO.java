package com.duoc.LevelUp.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class NoticiaImagenCreateDTO {

    @NotBlank
    private String nombreArchivo;

    @NotBlank
    private String contentType; // ej: image/png

    @NotBlank
    private String base64;      // imagen codificada en base64
}
