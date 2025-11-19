package com.duoc.LevelUp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "noticia_imagen")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class NoticiaImagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen_noticia")
    private Long idImagenNoticia;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(
            name = "id_noticia",
            nullable = false,
            foreignKey = @ForeignKey(name = "noticia_imagen_noticia_fk"))
    private Noticia noticia;

    @Column(name = "nombre_archivo",nullable = false,length = 500)
    private String nombreArchivo;

    @Column(name = "content_type",nullable = false,length = 30)
    private String contentType;

    @Column(name = "size_bytes",nullable = false)
    private Long sizeBytes;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data",nullable = false,columnDefinition = "BYTEA")
    private byte[] data;
}
