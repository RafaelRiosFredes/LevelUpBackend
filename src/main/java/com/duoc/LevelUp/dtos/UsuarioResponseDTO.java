package com.duoc.LevelUp.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private Long idUsuario;
    private String nombres;
    private String apellidos;
    private String correo;
    private Long telefono;
    private LocalDate fechaNacimiento;
}
