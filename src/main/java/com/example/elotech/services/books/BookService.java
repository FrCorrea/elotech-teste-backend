package com.example.elotech.services.books;

import com.example.elotech.domain.dtos.book.BookRequestDto;
import com.example.elotech.domain.dtos.book.BookResponseDto;

import java.util.List;

public interface BookService {
    BookResponseDto save(BookRequestDto bookRequestDto);
    BookResponseDto update(BookRequestDto bookRequestDto);
    void delete(Long id);
    List<BookResponseDto> getAll();
    BookResponseDto getByIsbn(String isbn);
    BookResponseDto getByTitle(String title);
}
