package com.librarymknw.bookService;

import com.librarymknw.bookService.application.BookServiceMainApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("local")
@SpringBootTest(classes = BookServiceMainApplication.class)
class BookServiceMainApplicationTests {

	@Test
	void contextLoads() {
	}

}
