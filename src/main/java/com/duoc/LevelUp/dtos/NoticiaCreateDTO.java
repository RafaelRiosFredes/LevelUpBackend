package com.duoc.LevelUp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor

public class NoticiaCreateDTO {
    @NotBlank
    private String titulo;

    @NotBlank
    private String descripcion;

    @NotBlank
    private String autor;

    // en JSON va como "yyyy-MM-dd"
    @NotNull
    private java.time.LocalDate fechaPublicacion;
}
