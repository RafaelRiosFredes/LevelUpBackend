package com.duoc.LevelUp.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsuarioDTO {
    @NotBlank
    String nombres;

    @NotBlank
    String apellidos;

    @NotBlank
    @Email
    String correo;

    @NotBlank
    String contrasena;

    @NotNull
    @Positive
    Long telefono;

    @NotNull
    @Past
    LocalDate fechaNacimiento;
}
