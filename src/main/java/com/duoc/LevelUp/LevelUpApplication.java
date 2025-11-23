package com.duoc.LevelUp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "LevelUp API",
				version = "1.0",
				description = "Documentación de la API para LevelUp"
		)
)
public class LevelUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(LevelUpApplication.class, args);
	}

}
