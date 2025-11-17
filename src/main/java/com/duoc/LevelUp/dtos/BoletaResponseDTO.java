package com.duoc.LevelUp.dtos;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
public class BoletaResponseDTO {
    private Long idBoleta;
    private LocalDateTime fechaEmision;

    private Long idUsuario;
    private String nombreUsuario;

    private Long totalSinDescuento;
    private Integer descuento;          // porcentaje (0 o 20)
    private Long total;                 // total ya con descuento

    private Boolean descuentoDuocAplicado;

    private List<DetalleBoletaResponseDTO> detalles;
}
