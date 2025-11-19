package com.duoc.LevelUp.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OpinionId implements Serializable {
    @Column(name = "producto_id_producto")
    private Long idProducto;

    @Column(name = "usuario_id_usuario")
    private Long idUsuario;
}
