package com.duoc.LevelUp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "producto")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre_producto",nullable = false)
    @NotBlank(message = "El campo 'nombre de producto' no puede ser vacio")
    private String nombreProducto;

    @Column(name = "descripcion",nullable = false)
    @NotBlank(message = "El campo 'descripcion' no puede ser vacio")
    private String descripcion;

    @Column(name = "precio",nullable = false)
    @NotNull(message = "El campo 'precio' no puede ser vacio")
    private Long precio;

    @Column(name = "stock",nullable = false)
    @NotNull(message = "El campo 'stock' no puede ser vacio")
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "id_categoria",nullable = false,
    foreignKey = @ForeignKey(name = "fk_producto_categoria"))
    @NotNull(message = "El campo 'categoria' no puede ser vacio")
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductoImagen> imagenes = new ArrayList<>();

    @OneToMany(mappedBy = "producto",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Opinion> opiniones = new ArrayList<>();
}
