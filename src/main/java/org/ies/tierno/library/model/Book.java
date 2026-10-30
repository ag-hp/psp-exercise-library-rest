package org.ies.tierno.library.model;

public record Book(Long id, String isbn, String title, String author, int publicationYear, int pages) {
}