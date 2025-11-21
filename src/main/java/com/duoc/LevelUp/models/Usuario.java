package com.duoc.LevelUp.models;

import com.duoc.LevelUp.models.audit.Audit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usuario")
@Getter @Setter
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
    @NotBlank(message = "EL campo de correo es obligatorio (es el nombre de usuario)")
    private String correo;

    @Column(name = "contrasena",nullable = false,length = 200)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "El campo de contraseña es obligatorio")
    private String contrasena;

    @Column(name = "telefono")
    private Long telefono;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "duoc")
    private Boolean duoc;

    private Boolean enabled;

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean admin;

    @Embedded
    private Audit audit = new Audit();

    @Column(name = "desc_apl")
    private Boolean descApl;

    @OneToMany(mappedBy = "usuario",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Opinion> opiniones = new ArrayList<>();

    @JsonIgnoreProperties({"usuarios"})
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "usuario_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_usuario","id_rol"})}
    )
    private List<Rol> roles;

    public Usuario(){ this.roles = new ArrayList<>(); }

    public Usuario(String correo,
                   String contrasena)
    {
        this();
        this.correo = correo;
        this.contrasena = contrasena;
        this.enabled = true;
    }

    @Override
    public String toString(){
        return "{"+
                "id=" + idUsuario +
                ", correo=" + correo + '\'' +
                ", createdAt=" + audit.getCreatedAt() +
                ", updatedAt=" + audit.getUpdatedAt()+
                '}';
    }

    @PrePersist
    public void prePersist(){this.enabled = true;}

    @Override
    public boolean equals(Object o){
        if (o == null || getClass() != o.getClass()) return  false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(idUsuario, usuario.idUsuario) && Objects.equals(correo, usuario.correo);
    }

    @Override
    public int hashCode(){return Objects.hash(idUsuario,correo);}
}
