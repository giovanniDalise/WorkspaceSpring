package com.librarymknw.bookService.infrastructure.persistence.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "book")
public class BookEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "editor")
    private EditorEntity editor;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
// gestisco anche le modifiche su entità esistenti con all, diversamente da persist che agisce solo su entità non esistenti
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book"),
            inverseJoinColumns = @JoinColumn(name = "author")
    )
    private Set<AuthorEntity> authors = new HashSet<>();

    @Column(name = "title")
    private String title;

    @Column(name = "ISBN")
    private String isbn;

    public BookEntity() {
    }

    public BookEntity(String title, String isbn, EditorEntity editor, AuthorEntity[] authors) {
        this.title = title;
        this.isbn = isbn;
        this.editor = editor;
        if (authors != null) {
            this.authors.addAll(Arrays.asList(authors));
        } else {
            this.authors = new HashSet<>();
        }
    }

    public EditorEntity getEditor() {
        return editor;
    }

    public void setEditor(EditorEntity editor) {
        this.editor = editor;
    }

    public AuthorEntity[] getAuthorsArray() {
        return authors.toArray(new AuthorEntity[authors.size()]);
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Set<AuthorEntity> getAuthorsSet() {
        return authors;
    }

    public void setAuthors(Set<AuthorEntity> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Set<AuthorEntity> getAuthors() {
        return authors;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookEntity book = (BookEntity) o;
        return Objects.equals(bookId, book.bookId);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(bookId);
    }

    public String toString() {
        return title + ", " + isbn + ", " + editor + ", " + authors.toString();
    }
}

