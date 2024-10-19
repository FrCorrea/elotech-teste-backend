package com.example.elotech.domain.dtos.loan;

import com.example.elotech.domain.Book;
import com.example.elotech.domain.Users;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

public record LoanRequestDto(
        Long id,
        @NotNull(message = "O usuário é obrigatório")
        Long userId,

        @NotNull(message = "O livro é obrigatória")
        Long bookId,

        @NotBlank(message = "A date de retorno é obrigatória")
        @Future(message = "A data de retorno deve ser no futuro")
        LocalDate returnDate,
        String status
) {
}
