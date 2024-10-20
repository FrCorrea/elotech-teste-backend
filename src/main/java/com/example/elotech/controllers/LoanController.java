package com.example.elotech.controllers;

import com.example.elotech.domain.dtos.loan.LoanRequestDto;
import com.example.elotech.domain.dtos.loan.LoanResponseDto;
import com.example.elotech.services.loans.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
@Tag(name = "Loan Controller", description = "APIs para gerenciamento de empréstimos de livros")
public class LoanController {
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(summary = "Registrar empréstimo", description = "Registra um novo empréstimo no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo registrado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content)
    })
    @PostMapping("/save")
    public LoanResponseDto saveLoan(@RequestBody @Valid LoanRequestDto loansDto) {
        return this.loanService.save(loansDto);
    }

    @Operation(summary = "Retornar empréstimo", description = "Marca um empréstimo como retornado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo retornado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content)
    })
    @PutMapping("/return")
    public LoanResponseDto returnLoan(@RequestBody LoanRequestDto loansDto) {
        return this.loanService.returnLoan(loansDto);
    }

    @Operation(summary = "Listar todos os empréstimos", description = "Lista todos os empréstimos registrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empréstimos retornada com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDto.class))})
    })
    @GetMapping
    public List<LoanResponseDto> getAll() {
        return this.loanService.getAll();
    }

    @Operation(summary = "Buscar empréstimo por ID", description = "Busca um empréstimo pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empréstimo encontrado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado", content = @Content)
    })
    @GetMapping("/getById/{id}")
    public LoanResponseDto getById(@PathVariable @Valid @NotNull(message = "O id é obrigatório")
                                   @Parameter(description = "ID do empréstimo a ser buscado") Long id) {
        return this.loanService.getById(id);
    }

    @Operation(summary = "Buscar empréstimos por ID do usuário", description = "Busca todos os empréstimos de um usuário específico pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empréstimos retornada com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Empréstimos não encontrados", content = @Content)
    })
    @GetMapping("/getByUserId/{userId}")
    public List<LoanResponseDto> getByUserId(@PathVariable @Valid @NotNull(message = "O id é obrigatório")
                                             @Parameter(description = "ID do usuário para buscar os empréstimos") Long userId) {
        return this.loanService.getByUserId(userId);
    }

    @Operation(summary = "Buscar empréstimos por ID do livro", description = "Busca todos os empréstimos de um livro específico pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empréstimos retornada com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Empréstimos não encontrados", content = @Content)
    })
    @GetMapping("/getByBookId/{bookId}")
    public List<LoanResponseDto> getByBookId(@PathVariable @Valid @NotNull(message = "O id é obrigatório")
                                             @Parameter(description = "ID do livro para buscar os empréstimos") Long bookId) {
        return this.loanService.getByBookId(bookId);
    }
}
