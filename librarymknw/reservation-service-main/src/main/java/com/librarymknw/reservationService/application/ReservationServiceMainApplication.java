package com.librarymknw.reservationService.application;

import com.librarymknw.reservationService.core.domain.models.Reservation;
import com.librarymknw.reservationService.core.ports.ReservationRepositoryPort;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@ComponentScan(basePackages = "com.librarymknw.reservationService")
@MapperScan("com.librarymknw.reservationService.infrastructure.mappers")
@SpringBootApplication
public class ReservationServiceMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceMainApplication.class, args);
	}

    @Bean
	public CommandLineRunner demo(ReservationRepositoryPort repository) {
		return (args) -> {
			System.out.println("Fetching all books from the repository:");

			List<Reservation> books = repository.findAll();
			if (books.isEmpty()) {
				System.out.println("No books found.");
			} else {
				books.forEach(book -> System.out.println(book));
			}
		};
	}
}
