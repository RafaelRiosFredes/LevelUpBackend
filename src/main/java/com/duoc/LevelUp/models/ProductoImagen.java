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
    @Column(name = "id_imagen")
    private Long idImagen;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "producto_id_producto",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_imagen_producto")
    )
    private Producto producto;

    @Column(name = "nombre_archivo", nullable = false)
    private String nombreArchivo;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size_bytes", nullable = false)
    private Long sizeBytes;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data", nullable = false, columnDefinition = "BYTEA")
    private byte[] data;
}
