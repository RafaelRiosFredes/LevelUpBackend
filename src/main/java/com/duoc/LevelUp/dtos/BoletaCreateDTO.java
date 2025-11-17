package com.duoc.LevelUp.dtos;


import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class BoletaCreateDTO {
    @NotEmpty
    private List<BoletaItemDTO> items;

    @NotNull
    @PositiveOrZero
    private Long total;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer descuento;
}
