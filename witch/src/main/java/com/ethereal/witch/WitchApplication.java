package com.ethereal.witch;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
@OpenAPIDefinition
public class WitchApplication {

	public static void main(String[] args) {
		SpringApplication.run(WitchApplication.class, args);
	}

}
