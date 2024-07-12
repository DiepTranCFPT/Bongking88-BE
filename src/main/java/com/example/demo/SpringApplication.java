package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Booking88 API", version = "2.0", description = "Information"))
@SecurityScheme(name = "api", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@EnableScheduling
public class SpringApplication {

	public static void main(String[] args) {
		org.springframework.boot.SpringApplication.run(SpringApplication.class,args);
	}



}
