package com.example.elotech.controllers;

import com.example.elotech.domain.dtos.users.UsersRequestDto;
import com.example.elotech.domain.dtos.users.UsersResponseDto;
import com.example.elotech.services.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Users Controller", description = "APIs para gerenciamento de usuários")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Registrar usuário", description = "Registra um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<UsersResponseDto> registerUser(@RequestBody @Valid UsersRequestDto user) {
        return ResponseEntity.ok(this.userService.save(user));
    }


    @Operation(summary = "Atualizar usuário", description = "Atualiza um usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @PutMapping("/update")
    public ResponseEntity<UsersResponseDto> updateUser(@RequestBody @Valid UsersRequestDto user) {
        return ResponseEntity.ok(this.userService.update(user));
    }

    @Operation(summary = "Listar todos os usuários", description = "Lista todos os usuários registrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponseDto.class))})
    })
    @GetMapping
    public ResponseEntity<List<UsersResponseDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAll());
    }

    @Operation(summary = "Buscar usuário por ID", description = "Busca um usuário pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UsersResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<UsersResponseDto> getUserById(@PathVariable @Valid
                                                        @NotNull(message = "O id é obrigatório")
                                                        @Parameter(description = "ID do usuário a ser buscado") Long id) {
        return ResponseEntity.ok(this.userService.getById(id));
    }

    @Operation(summary = "Deletar usuário", description = "Deleta um usuário pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Valid @NotNull(message = "O id é obrigatório")
                                           @Parameter(description = "ID do usuário a ser deletado") Long id) {
        this.userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
