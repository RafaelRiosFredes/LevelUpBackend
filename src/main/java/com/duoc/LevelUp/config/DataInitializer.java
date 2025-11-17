package com.duoc.LevelUp.config;

import com.duoc.LevelUp.models.Rol;
import com.duoc.LevelUp.models.Usuario;
import com.duoc.LevelUp.repositories.RolRepository;
import com.duoc.LevelUp.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final RolRepository rolRepo;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initData(){
        return args ->{
            // crear roles si no existen
            Rol userRol = rolRepo.findByNombreRol("USER")
                    .orElseGet(()->{
                        Rol r = new Rol();
                        r.setNombreRol("USER");
                        return rolRepo.save(r);
                    });
            Rol adminRol = rolRepo.findByNombreRol("ADMIN")
                    .orElseGet(()->{
                        Rol r = new Rol();
                        r.setNombreRol("ADMIN");
                        return rolRepo.save(r);
                    });
            // crear usuario admin si no existe
            String adminCorreo = "admin@levelup.cl";

            if(!usuarioRepo.existsByCorreoIgnoreCase(adminCorreo)){
                Usuario admin = new Usuario();
                admin.setNombres("Admin");
                admin.setApellidos("LevelUp");
                admin.setCorreo(adminCorreo);
                admin.setContrasena(passwordEncoder.encode("Admin123"));
                admin.setTelefono(123456789L);
                admin.setFechaNacimiento(LocalDate.of(1990,1,1));
                admin.setDuoc(false);
                admin.setDescApl(false);

                // si la coleccion ya viene inicializada en la entidad:
                admin.getRoles().add(userRol);
                admin.getRoles().add(adminRol);

                // si no viene inicializada:
                // admin.setRoles(new HashSet<>(Set.of(adminRol)));

                usuarioRepo.save(admin);
            }
        };
    }
}
