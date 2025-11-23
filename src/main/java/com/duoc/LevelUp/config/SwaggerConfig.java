package com.duoc.LevelUp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * Configura ViewControllers para manejar la redirección de la URL base de Swagger UI.
     * Esto es necesario para asegurar que tanto "/swagger-ui" como "/swagger-ui/"
     * resuelvan correctamente al archivo index.html.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // 1. Redirige la URL sin barra final (/swagger-ui) a su index.html real (con HTTP 302 Found)
        registry.addViewController("/swagger-ui").setViewName("redirect:/swagger-ui/index.html");

        // 2. Asegura el mapeo de la ruta con la barra final (forward interno)
        registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
    }
}