package com.example.elotech.controllers;

import com.example.elotech.domain.dtos.book.BookResponseDto;
import com.example.elotech.services.recommendation.RecommendationService;
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
@RequestMapping("/recommendations")
@Tag(name = "Recommendation Controller", description = "APIs para recomendação de livros para usuários")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(summary = "Obter recomendações de livros", description = "Gera uma lista de recomendações de livros para um usuário específico com base no histórico de empréstimos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recomendações retornadas com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BookResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)
    })
    @GetMapping("/{userId}")
    public List<BookResponseDto> getRecommendations(@PathVariable @Valid
                                                    @NotNull(message = "O id é obrigatório")
                                                    @Parameter(description = "ID do usuário para obter recomendações de livros") Long userId) {
        return this.recommendationService.getRecommendations(userId);

    }
}
