package com.example.elotech.controllers;


import com.example.elotech.domain.dtos.users.UsersRequestDto;
import com.example.elotech.domain.dtos.users.UsersResponseDto;
import com.example.elotech.services.users.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UsersResponseDto> registerUser(@RequestBody @Valid UsersRequestDto user) {
        return ResponseEntity.ok(this.userService.save(user));
    }

    @GetMapping
    public ResponseEntity<List<UsersResponseDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UsersResponseDto> getUserById(@PathVariable @Valid
                                                            @NotNull(message = "O id é obrigatório") Long id) {
        return ResponseEntity.ok(this.userService.getById(id));
    }

   @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Valid @NotNull(message = "O id é obrigatório")
                                              Long id) {
        this.userService.delete(id);
        return ResponseEntity.ok().build();
    }


}
