package com.duoc.LevelUp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ProductoImagenCreateDTO {
    @NotBlank
    private String nombreArchivo;

    @NotBlank
    private String contentType; // ej imagen/png

    @NotBlank
    private String base64;
}
