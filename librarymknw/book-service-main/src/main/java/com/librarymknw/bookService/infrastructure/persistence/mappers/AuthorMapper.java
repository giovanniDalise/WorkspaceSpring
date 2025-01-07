package com.librarymknw.bookService.infrastructure.persistence.mappers;

import com.librarymknw.bookService.core.domain.models.Author;
import com.librarymknw.bookService.infrastructure.persistence.entities.AuthorEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    public static Author toDomain(AuthorEntity entity) {
        if (entity == null) return null;
        Author author = new Author();
        author.setId(entity.getId());
        author.setName(entity.getName());
        author.setSurname(entity.getSurname());
        // Books mapping can be added if necessary
        return author;
    }

    public static AuthorEntity toEntity(Author author) {
        if (author == null) return null;
        AuthorEntity entity = new AuthorEntity();
        entity.setId(author.getId());
        entity.setName(author.getName());
        entity.setSurname(author.getSurname());
        // Books mapping can be added if necessary
        return entity;
    }

    public static Set<Author> toDomainSet(Set<AuthorEntity> entities) {
        if (entities == null) return null;
        return entities.stream()
                .map(AuthorMapper::toDomain)
                .collect(Collectors.toSet());
    }

    public static Set<AuthorEntity> toEntitySet(Set<Author> authors) {
        if (authors == null) return null;
        return authors.stream()
                .map(AuthorMapper::toEntity)
                .collect(Collectors.toSet());
    }
}
