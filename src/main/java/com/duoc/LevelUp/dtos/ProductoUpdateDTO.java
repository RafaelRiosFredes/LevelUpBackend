package com.duoc.LevelUp.dtos;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class ProductoUpdateDTO {
    @NotBlank
    private String nombreProducto;
    @NotBlank
    private String descripcion;

    @NotNull
    @Positive
    private Long precio;

    @NotNull
    @PositiveOrZero
    private Integer stock;

    @NotNull
    private Long categoriaId;
}
