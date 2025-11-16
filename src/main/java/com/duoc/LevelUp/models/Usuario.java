package com.duoc.LevelUp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombres",nullable = false,length = 100)
    private String nombres;

    @Column(name = "apellidos",nullable = false,length = 100)
    private String apellidos;

    @Column(name = "correo",nullable = false,length = 50,unique = true)
    private String correo;

    @Column(name = "contrasena",nullable = false,length = 200)
    private String contrasena;

    @Column(name = "telefono")
    private Long telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "duoc")
    private Boolean duoc;

    @Column(name = "desc_apl")
    private Boolean descApl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "rol_id_rol")
    )
    private Set<Rol> roles = new HashSet<>();
}
