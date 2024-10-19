package com.example.elotech.controllers;


import com.example.elotech.domain.dtos.book.BookResponseDto;
import com.example.elotech.services.recommendation.RecommendationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public List<BookResponseDto> getRecommendations(@PathVariable @Valid
                                                        @NotNull(message = "O id é obrigatório") Long userId) {
        return this.recommendationService.getRecommendations(userId);
    }
}
