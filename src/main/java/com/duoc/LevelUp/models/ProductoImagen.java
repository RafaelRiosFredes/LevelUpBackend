package com.duoc.LevelUp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "producto_imagen")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductoImagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_IMAGEN")
    private Long idImagen;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PRODUCTO", nullable = false,
            foreignKey = @ForeignKey(name = "fk_imagen_producto"))
    private Producto producto;

    @Column(name = "NOMBRE_ARCHIVO",nullable = false)
    private String nombreArchivo;

    @Column(name = "CONTENT_TYPE",nullable = false)
    private String contentType;

    @Column(name = "SIZE_BYTES",nullable = false)
    private Long sizeBytes;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "DATA",nullable = false,columnDefinition = "BYTEA")
    private byte[] data;
}
