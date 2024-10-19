package com.example.elotech.mappers;


import com.example.elotech.domain.Book;
import com.example.elotech.domain.dtos.book.BookRequestDto;
import com.example.elotech.domain.dtos.book.BookResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", source = "id")
    Book toEntity(BookRequestDto bookRequestDto);

    BookResponseDto toResponseDto(Book book);

    List<BookResponseDto> toResponseDtoList(List<Book> books);
}
