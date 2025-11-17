package com.duoc.LevelUp.dtos;
import lombok.*;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DetalleBoletaResponseDTO {
    private Long idProducto;
    private String nombreProducto;
    private Long precioUnitario;
    private Integer cantidad;
    private Long subtotal;
}
