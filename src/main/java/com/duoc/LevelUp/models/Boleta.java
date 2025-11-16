package com.duoc.LevelUp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "boleta")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleta")
    private Long idBoleta;

    @Column(name = "descuento")
    private Integer descuento;

    @Column(name = "total",nullable = false)
    private Long total;

    @Column(name = "fecha_emision")
    private java.time.LocalDateTime fechaEmision;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id_usuario",
    foreignKey = @ForeignKey(name = "boleta_usuario_fk"))
    private Usuario usuario;

    @OneToMany(mappedBy = "boleta",cascade = CascadeType.ALL,orphanRemoval = true)
    private java.util.List<DetalleBoleta> detalles = new java.util.ArrayList<>();
}
