package com.example.elotech.services.books;


import com.example.elotech.domain.Book;
import com.example.elotech.domain.dtos.book.BookRequestDto;
import com.example.elotech.domain.dtos.book.BookResponseDto;
import com.example.elotech.exceptions.handlers.DatabaseOperationException;
import com.example.elotech.exceptions.handlers.ResourceNotFoundException;
import com.example.elotech.mappers.BookMapper;
import com.example.elotech.repositories.BookRepository;
import org.springframework.stereotype.Service;

import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookResponseDto save(BookRequestDto bookRequestDto) {
        try {
            Book book = bookMapper.toEntity(bookRequestDto);
            book = bookRepository.save(book);
            return bookMapper.toResponseDto(book);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Erro ao salvar o livro");
        }
    }

    @Override
    public List<BookResponseDto> getAll() {
        try {
            List<Book> books = this.bookRepository.findAll();
            return books.isEmpty() ? null : bookMapper.toResponseDtoList(books);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Erro ao buscar todos os livros");
        }
    }
    @Override
    public BookResponseDto update(BookRequestDto bookRequestDto) {
        try {
            Book book = bookMapper.toEntity(bookRequestDto);
            book = bookRepository.save(book);
            return bookMapper.toResponseDto(book);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Erro ao atualizar o livro");
        }

    }
    @Override
    public void delete(Long id) {
        try {
            if (this.bookRepository.findById(id).isEmpty()) {
                throw new ResourceNotFoundException("Livro não encontrado");
            }
            this.bookRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Erro ao excluir o livro");
        }
    }

    @Override
    public BookResponseDto getByIsbn(String isbn) {
        try {
            Optional<Book> optionalBook = this.bookRepository.findByIsbn(isbn);
            Book book = optionalBook.orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
            return bookMapper.toResponseDto(book);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Erro ao buscar o livro pelo isbn");
        }
    }
    @Override
    public BookResponseDto getByTitle(String title) {
        try {
            Optional<Book> optionalBook = this.bookRepository.findByTitle(title);
            Book book = optionalBook.orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
            return bookMapper.toResponseDto(book);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Erro ao buscar o livro pelo nome");
        }
    }

}
