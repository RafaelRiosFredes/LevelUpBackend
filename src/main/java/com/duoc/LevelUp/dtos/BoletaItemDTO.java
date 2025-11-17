package com.duoc.LevelUp.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor @AllArgsConstructor
public class BoletaItemDTO {
    @NotNull
    private Long idProducto;

    @NotNull
    @Positive
    private Integer cantidad;
}
