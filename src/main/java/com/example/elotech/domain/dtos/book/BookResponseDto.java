package com.example.elotech.domain.dtos.book;

import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookResponseDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publishDate;
    private String category;
}
