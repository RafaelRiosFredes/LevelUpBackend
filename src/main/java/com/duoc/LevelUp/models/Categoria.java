package com.duoc.LevelUp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CATEGORIA")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CATEGORIA")
    private Long idCategoria;

    @Column(name = "NOMBRE_CATEGORIA",nullable = false)
    @NotNull(message = "El campo 'nombre de categoria' no puede ser vacio")
    private String nombreCategoria;
}
