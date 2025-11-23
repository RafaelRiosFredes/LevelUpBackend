package com.duoc.LevelUp.dtos;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private String username;
    private Object roles;
    private String message;
    private Long idUsuario;
}
