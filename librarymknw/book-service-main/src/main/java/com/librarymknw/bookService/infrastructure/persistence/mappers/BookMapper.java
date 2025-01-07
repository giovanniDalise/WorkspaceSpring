package com.librarymknw.bookService.infrastructure.persistence.mappers;

import com.librarymknw.bookService.core.domain.models.Book;
import com.librarymknw.bookService.infrastructure.persistence.entities.BookEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {
    public static Book toDomain(BookEntity entity) {
        return new Book(
                entity.getTitle(),
                entity.getIsbn(),
                EditorMapper.toDomain(entity.getEditor()),
                AuthorMapper.toDomainSet(entity.getAuthors())
        );
    }

    public static BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setTitle(book.getTitle());
        entity.setIsbn(book.getIsbn());
        entity.setEditor(EditorMapper.toEntity(book.getEditor()));
        entity.setAuthors(AuthorMapper.toEntitySet(book.getAuthors()));
        return entity;
    }

    public static List<Book> toDomainList(List<BookEntity> entities) {
        return entities.stream()
                .map(BookMapper::toDomain)
                .collect(Collectors.toList());
    }

    public static List<BookEntity> toEntityList(List<Book> books) {
        return books.stream()
                .map(BookMapper::toEntity)
                .collect(Collectors.toList());
    }
/* Senza utilizzare gli stream, dovresti usare un approccio più tradizionale, iterando manualmente su
   ciascun elemento della lista con un ciclo for. Questo rende il codice più lungo e meno elegante.
   Ti faccio un esempio come sarebbe uno dei due metodi senza lo stream
    public static List<BookEntity> toEntityList(List<Book> books) {
        List<BookEntity> entities = new ArrayList<>(); // Crea una nuova lista vuota di BookEntity
        for (Book book : books) {                      // Itera su ogni elemento della lista books
            entities.add(toEntity(book));             // Converte il Book in un BookEntity e lo aggiunge alla lista entities
        }
        return entities;                              // Ritorna la lista di BookEntity
    }*/
}
