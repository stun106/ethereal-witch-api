package com.ethereal.witch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;

@SpringBootApplication
public class WitchApplication {

	public static void main(String[] args) {
		SpringApplication.run(WitchApplication.class, args);
	}

}
