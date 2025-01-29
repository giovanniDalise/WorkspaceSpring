package com.librarymknw.authService.application;

import com.librarymknw.authService.core.domain.services.AuthenticationService;
import com.librarymknw.authService.core.ports.AuthenticationRepositoryPort;
import com.librarymknw.authService.core.ports.AuthenticationServicePort;
import com.librarymknw.authService.core.ports.JwtPort;
import com.librarymknw.authService.infrastructure.adapters.jwt.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.librarymknw.authService")
@EnableConfigurationProperties(JwtConfig.class)
@SpringBootApplication
public class AuthServiceMainApplication {

	public static void main(String[] args) {

		SpringApplication.run(AuthServiceMainApplication.class, args);
	}

	@Bean
	public AuthenticationService authenticationService(JwtPort jwtPort, AuthenticationRepositoryPort repositoryPort) {
		return new AuthenticationService(jwtPort, repositoryPort);
	}

}
