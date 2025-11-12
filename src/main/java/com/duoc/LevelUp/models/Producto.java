package com.duoc.LevelUp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCTO")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long idProducto;

    @Column(name = "NOMBRE_PRODUCTO",nullable = false)
    @NotNull(message = "El campo 'nombre de producto' no puede ser vacio")
    private String nombreProducto;

    @Column(name = "DESCRIPCION",nullable = false)
    @NotNull(message = "El campo 'descripcion' no puede ser vacio")
    private String descripcion;

    @Column(name = "PRECIO",nullable = false)
    @NotNull(message = "El campo 'precio' no puede ser vacio")
    private Long precio;

    @Column(name = "STOCK",nullable = false)
    @NotNull(message = "El campo 'stock' no puede ser vacio")
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "ID_CATEGORIA",nullable = false,
    foreignKey = @ForeignKey(name = "fk_producto_categoria"))
    @NotNull(message = "El campo 'categoria' no puede ser vacio")
    private Categoria categoria;

    @OneToMany(mappedBy = "PRODUCTO", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ProductoImagen> imagenes = new ArrayList<>();
}
