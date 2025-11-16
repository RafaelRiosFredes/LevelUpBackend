package com.duoc.LevelUp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class DetalleBoletaId implements java.io.Serializable{
    @Column(name = "boleta_id_boleta")
    private Long idBoleta;

    @Column(name = "producto_id_producto")
    private Long idProducto;
}
