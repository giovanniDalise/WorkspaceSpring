package com.librarymknw.userService.application;

import com.librarymknw.userService.core.domain.models.User;
import com.librarymknw.userService.core.domain.services.UserService;
import com.librarymknw.userService.core.ports.UserRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@ComponentScan(basePackages = "com.librarymknw.userService")
@SpringBootApplication
public class UserServiceMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceMainApplication.class, args);
	}

	@Bean
	public UserService userService(UserRepositoryPort userRepositoryPort) {
		return new UserService(userRepositoryPort); // Iniettiamo BookRepositoryPort
	}

/*    @Bean
	public CommandLineRunner demo(UserRepositoryPort repository) {
		return (args) -> {
			System.out.println("Fetching all books from the repository:");

			List<User> books = repository.findAll();
			if (books.isEmpty()) {
				System.out.println("No books found.");
			} else {
				books.forEach(book -> System.out.println(book));
			}
		};
	}*/
}
