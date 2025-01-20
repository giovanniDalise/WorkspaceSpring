package com.librarymknw.bookService.core.domain.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Book {

    private Long bookId;
    private String title;
    private String isbn;
    private Editor editor;
    private Set<Author> authors = new HashSet<>();

    // Costruttore predefinito
    public Book() {}

    // Costruttore che accetta Set<Author>
    public Book(Long bookId, String title, String isbn, Editor editor, Set<Author> authors) {
        this.bookId = bookId;
        this.title = title;
        this.isbn = isbn;
        this.editor = editor;
        this.authors = (authors != null) ? authors : new HashSet<>();
    }

    // Getter e setter
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Editor getEditor() {
        return editor;
    }

    public void setEditor(Editor editor) {
        this.editor = editor;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = (authors != null) ? authors : new HashSet<>();
    }

    // Equals e hashCode basati su bookId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(bookId, book.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(bookId);
    }

    // Metodo toString per debug
    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", editor=" + editor +
                ", authors=" + authors +
                '}';
    }
}
