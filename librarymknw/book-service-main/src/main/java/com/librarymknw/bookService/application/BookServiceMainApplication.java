package com.librarymknw.bookService.application;

import com.librarymknw.bookService.core.domain.models.Book;
import com.librarymknw.bookService.core.domain.services.BookService;
import com.librarymknw.bookService.core.ports.BookRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@ComponentScan(basePackages = "com.librarymknw.bookService")
@EntityScan(basePackages = "com.librarymknw.bookService.infrastructure.persistence.entities") // Cambia con il pacchetto corretto
@SpringBootApplication
public class BookServiceMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceMainApplication.class, args);
	}
	@Bean
	public BookService bookService(BookRepositoryPort bookRepositoryPort) {
		return new BookService(bookRepositoryPort); // Iniettiamo BookRepositoryPort
	}

/*	@Bean
	public CommandLineRunner demo(BookRepositoryPort repository) {
		return (args) -> {

			Long idBook = repository.delete(6L);
		};
	}*/


}
