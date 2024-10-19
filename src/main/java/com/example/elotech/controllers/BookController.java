package com.example.elotech.controllers;


import com.example.elotech.domain.dtos.book.BookRequestDto;
import com.example.elotech.domain.dtos.book.BookResponseDto;
import com.example.elotech.services.books.BookService;
import com.example.elotech.services.googleBooks.GoogleBooksService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")

public class BookController {
    private final BookService bookService;
    private final GoogleBooksService googleBooksService;

    public BookController(BookService bookService, GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
        this.bookService = bookService;
    }

    @GetMapping("/getForGoogle")
    public ResponseEntity<Object> getBooksForGoogle(@RequestParam @NotBlank(message = "O nome é obrigatório")
                                                     String title) {
        return ResponseEntity.ok(this.googleBooksService.searchBooks(title));
    }

    @PostMapping("/save")
    public ResponseEntity<BookResponseDto> saveBook(@RequestBody @Valid BookRequestDto book) {
        return ResponseEntity.ok(this.bookService.save(book));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        return ResponseEntity.ok(this.bookService.getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<BookResponseDto> updateBook(@RequestBody @Valid BookRequestDto book) {
        return ResponseEntity.ok(this.bookService.update(book));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable @NotNull(message = "O id é obrigatório") @Valid Long id) {
        this.bookService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/{isbn}")
    public ResponseEntity<BookResponseDto> getBookByIsbn(@PathVariable @Valid @NotBlank(message = "O isbn é obrigatório")
                                                             String isbn) {
        return ResponseEntity.ok(this.bookService.getByIsbn(isbn));
    }

    @GetMapping("/get/{title}")
    public ResponseEntity<BookResponseDto> getBookByTitle(@PathVariable @NotBlank(message = "O nome é obrigatório")
                                                              String name) {
        return ResponseEntity.ok(this.bookService.getByTitle(name));
    }

}
