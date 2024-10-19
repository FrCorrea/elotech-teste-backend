package com.example.elotech.services.recommendation;


import com.example.elotech.domain.dtos.book.BookResponseDto;

import java.util.List;

public interface RecommendationService {
    List<BookResponseDto> getRecommendations(Long userId);
}
