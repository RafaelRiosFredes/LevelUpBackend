package com.duoc.LevelUp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ProductoImagenCreateDTO {
    @NotBlank
    private String nombreArchivo;

    @NotBlank
    private String contentType;

    @NotBlank
    private String base64;
}
