package com.duoc.LevelUp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        // Redirige la URL
        registry.addViewController("/swagger-ui").setViewName("redirect:/swagger-ui/index.html");

        // Asegura el mapeo de la ruta
        registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
    }
}