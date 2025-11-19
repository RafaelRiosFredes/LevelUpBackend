package com.duoc.LevelUp.dtos;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class OpinionResponseDTO {
    private Long idProducto;
    private Long idUsuario;
    private String nombreUsuario;
    private String comentario;
    private Integer estrellas;
}
