package com.duoc.LevelUp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_boleta")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DetalleBoleta {
    @EmbeddedId
    private DetalleBoletaId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idBoleta")
    @JoinColumn(name = "boleta_id_boleta",
    foreignKey = @ForeignKey(name = "detalle_boleta_boleta_fk"))
    private Boleta boleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProducto")
    @JoinColumn(name = "producto_id_producto",
    foreignKey = @ForeignKey(name = "detalle_boleta_producto_fk"))
    private Producto producto;

    @Column(name = "cantidad",nullable = false)
    private Integer cantidad;
}
