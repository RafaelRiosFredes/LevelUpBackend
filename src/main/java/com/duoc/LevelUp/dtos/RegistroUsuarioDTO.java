package com.duoc.LevelUp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioDTO {
    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @NotBlank
    @Email
    private String correo;

    @NotBlank
    private String contrasena;

    @NotNull
    @Positive
    private Long telefono;

    @NotNull
    @Past
    private LocalDate fechaNacimiento;
}
