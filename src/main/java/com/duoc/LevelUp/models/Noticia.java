package com.duoc.LevelUp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "noticia")
@Getter @Setter
@NoArgsConstructor@AllArgsConstructor
public class Noticia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_noticia")
    private Long idNoticia;

    @Column(name = "titulo",nullable = false,length = 50)
    @NotBlank(message = "El titulo no puede estar vacío")
    private String titulo;

    @Column(name = "descripcion",nullable = false,length = 200)
    @NotBlank(message = "La descripcion no puede estar vacía")
    private String descripcion;

    @Column(name = "autor",nullable = false,length = 50)
    @NotBlank(message = "El autor no puede estar vacío")
    private String autor;

    @Column(name = "fecha_publicacion",nullable = false)
    @NotNull(message = "La fecha no puede estar vacía")
    private LocalDate fechaPublicacion;

    @OneToMany(
            mappedBy = "noticia",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<NoticiaImagen> imagenes = new ArrayList<>();
}
