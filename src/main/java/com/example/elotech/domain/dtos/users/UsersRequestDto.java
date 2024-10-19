package com.example.elotech.domain.dtos.users;

import com.example.elotech.domain.Users;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record UsersRequestDto(
        Long id,

        @NotBlank(message = "O nome é obrigatório")
        String name,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email é inválido")
        String email,
        LocalDateTime registerDate,
        @NotBlank(message = "O telefone é obrigatório")
        String phoneNumber
)
{
}
