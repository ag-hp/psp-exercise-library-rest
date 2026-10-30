package org.ies.tierno.library.controller;

import org.ies.tierno.library.controller.dto.BookRequest;
import org.ies.tierno.library.controller.dto.BookResponse;
import org.ies.tierno.library.model.Book;
import org.ies.tierno.library.service.BookService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<BookResponse> findAll(@RequestParam(required = false) String author) {
        return service.findAll(author).stream()
                .map(BookResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public BookResponse findById(@PathVariable long id) {
        return BookResponse.from(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        Book book = service.create(
                request.isbn(), request.title(), request.author(),
                request.year(), request.purchasePrice());
        URI location = URI.create("/books/" + book.id());
        return ResponseEntity.created(location).body(BookResponse.from(book));
    }

    @PutMapping("/{id}")
    public BookResponse replace(@PathVariable long id,
                                @Valid @RequestBody BookRequest request) {
        Book book = service.replace(
                id, request.isbn(), request.title(), request.author(),
                request.year(), request.purchasePrice());
        return BookResponse.from(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
