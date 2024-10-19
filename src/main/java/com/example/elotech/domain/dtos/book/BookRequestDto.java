package com.example.elotech.domain.dtos.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;

import java.time.LocalDate;

public record BookRequestDto(
        Long id,
        @NotBlank(message = "O título é obrigatório")
        String title,
        @NotBlank(message = "O autor é obrigatório")
        String author,
        @NotBlank(message = "O ISBN é obrigatório")
        String isbn,
        @NotNull(message = "A data de publicação é obrigatória")
        @PastOrPresent(message = "A data não pode ser futura")
        LocalDate publishDate,
        @NotBlank(message = "A categoria é obrigatória")
        String category

) {
}
