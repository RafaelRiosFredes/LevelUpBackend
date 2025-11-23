package com.duoc.LevelUp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * Configura ViewControllers para manejar la redirección a la interfaz gráfica de Swagger UI.
     * * Esto es necesario porque Spring Security y Spring Boot a veces no manejan correctamente
     * las rutas estáticas al usar SpringDoc.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // 1. Redirige la URL base /swagger-ui a su index.html real (con HTTP 302 Found)
        registry.addViewController("/swagger-ui").setViewName("redirect:/swagger-ui/index.html");

        // 2. Asegura el mapeo de la ruta final con la barra final (forward interno)
        // Esto previene errores 404/403 en algunas configuraciones de servidor.
        registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
    }
}