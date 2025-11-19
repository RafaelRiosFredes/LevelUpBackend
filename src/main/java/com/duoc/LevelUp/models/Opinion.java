package com.duoc.LevelUp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "opinion")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Opinion {
    @EmbeddedId
    private OpinionId idOpinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProducto")
    @JoinColumn(
            name = "producto_id_producto",
            foreignKey = @ForeignKey(name = "opinion_producto_fk")
    )
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idUsuario")
    @JoinColumn(
            name = "usuario_id_usuario",
            foreignKey = @ForeignKey(name = "opinion_usuario_fk")
    )
    private Usuario usuario;

    @Column(name = "comentario",length = 200)
    private String comentario;

    @Column(name = "estrellas",nullable = false)
    @Min(1) @Max(5)
    private Short estrellas;
}
