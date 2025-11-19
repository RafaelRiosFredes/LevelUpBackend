package com.duoc.LevelUp.dtos;

import lombok.*;

import jakarta.validation.constraints.*;

@Getter @Setter@ToString
@NoArgsConstructor @AllArgsConstructor
public class OpinionCreateDTO {
    @NotBlank
    private String comentario;

    @NotNull
    @Min(1) @Max(5)
    private Integer estrellas;
}
