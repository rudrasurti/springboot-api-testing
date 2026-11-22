package dev.rudra.books;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record Book(Long id, @NotBlank String title, @NotBlank String author, @Min(1) int pages) {
    public Book withId(Long newId) {
        return new Book(newId, title, author, pages);
    }
}
