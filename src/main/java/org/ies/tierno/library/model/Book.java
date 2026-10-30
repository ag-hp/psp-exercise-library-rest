package org.ies.tierno.library.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Book(int id, String isbn, String title, String author, int year, BigDecimal purchasePrice) {
    public Book(long l, String isbn, String title, String author, int year, BigDecimal purchasePrice, LocalDate now) {
    }

    public LocalDate registrationDate() {
    }
}
