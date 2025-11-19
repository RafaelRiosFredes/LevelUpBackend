package com.duoc.LevelUp.dtos;

import lombok.*;

import jakarta.validation.constraints.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class OpinionCreateDTO {
    @NotBlank
    private String comentario;

    @NotNull
    @Min(1) @Max(5)
    private Integer estrellas;
}
