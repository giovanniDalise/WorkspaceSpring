package com.librarymknw.bookService.core.domain.models;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Editor {

    private Long id;
    private String name;
    private Set<Book> books = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Editor() {
    }

    public Editor(String nome) {
        this.name = nome;
    }

    public boolean equals(Object appObj) {
        if (this == appObj) return true;
        if (!(appObj instanceof Editor)) return false;
        Editor e = (Editor) appObj;
        return this.name.equals(e.name);
    }

    public String toString() {
        return this.name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}