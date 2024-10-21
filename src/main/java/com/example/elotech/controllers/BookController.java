package com.example.elotech.controllers;

import com.example.elotech.domain.dtos.book.BookRequestDto;
import com.example.elotech.domain.dtos.book.BookResponseDto;
import com.example.elotech.services.books.BookService;
import com.example.elotech.services.googleBooks.GoogleBooksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Book Controller", description = "APIs para gerenciamento de livros e integração com Google Books")
public class BookController {
    private final BookService bookService;
    private final GoogleBooksService googleBooksService;

    public BookController(BookService bookService, GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
        this.bookService = bookService;
    }

    @Operation(summary = "Buscar livros no Google Books", description = "Busca livros na API do Google Books por título.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livros encontrados com sucesso", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content)
    })
    @GetMapping("/getForGoogle")
    public ResponseEntity<Object> getBooksForGoogle(@RequestParam @NotBlank(message = "O título é obrigatório")
                                                    @Parameter(description = "Título do livro a ser buscado") String title) {
        System.out.println(title);
        return ResponseEntity.ok(this.googleBooksService.searchBooks(title));
    }

    @Operation(summary = "Salvar livro", description = "Salva um novo livro no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro salvo com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content)
    })
    @PostMapping("/save")
    public ResponseEntity<BookResponseDto> saveBook(@RequestBody @Valid BookRequestDto book) {
        return ResponseEntity.ok(this.bookService.save(book));
    }

    @Operation(summary = "Listar todos os livros", description = "Lista todos os livros disponíveis no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getBooks() {
        return ResponseEntity.ok(this.bookService.getAll());
    }

    @Operation(summary = "Atualizar livro", description = "Atualiza um livro existente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<BookResponseDto> updateBook(@RequestBody @Valid BookRequestDto book) {
        return ResponseEntity.ok(this.bookService.update(book));
    }

    @Operation(summary = "Deletar livro", description = "Deleta um livro pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable @NotNull(message = "O id é obrigatório") @Valid Long id) {
        this.bookService.delete(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar livro por ISBN", description = "Busca um livro pelo seu ISBN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content)
    })
    @GetMapping("/getIsbn/{isbn}")
    public ResponseEntity<BookResponseDto> getBookByIsbn(@PathVariable @Valid @NotBlank(message = "O isbn é obrigatório")
                                                         @Parameter(description = "ISBN do livro a ser buscado") String isbn) {
        return ResponseEntity.ok(this.bookService.getByIsbn(isbn));
    }

    @Operation(summary = "Buscar livro por título", description = "Busca um livro pelo título.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", content = @Content)
    })
    @GetMapping("/getTitle/{title}")
    public ResponseEntity<BookResponseDto> getBookByTitle(@PathVariable @NotBlank(message = "O nome é obrigatório")
                                                          @Parameter(description = "Título do livro a ser buscado") String title) {
        return ResponseEntity.ok(this.bookService.getByTitle(title));
    }
}
