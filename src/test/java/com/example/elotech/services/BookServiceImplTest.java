package com.example.elotech.services;

import com.example.elotech.domain.Book;
import com.example.elotech.domain.dtos.book.BookRequestDto;
import com.example.elotech.domain.dtos.book.BookResponseDto;
import com.example.elotech.exceptions.handlers.DatabaseOperationException;
import com.example.elotech.exceptions.handlers.ResourceNotFoundException;
import com.example.elotech.mappers.BookMapper;
import com.example.elotech.repositories.BookRepository;
import com.example.elotech.services.books.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldSaveBookSuccessfully() {
        BookRequestDto requestDto = new BookRequestDto(null, "Title", "Author", "ISBN",
                LocalDate.now(), "Category");
        Book book = new Book();
        BookResponseDto responseDto = new BookResponseDto();

        when(bookMapper.toEntity(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        BookResponseDto result = bookService.save(requestDto);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void save_ShouldThrowDatabaseOperationException_WhenDataAccessExceptionOccurs() {
        BookRequestDto requestDto = new BookRequestDto(null, "Title", "Author", "ISBN",
                LocalDate.now(), "Category");

        when(bookMapper.toEntity(requestDto)).thenThrow(new DataIntegrityViolationException("Erro simulado"));

        assertThrows(DatabaseOperationException.class, () -> bookService.save(requestDto));
    }

    @Test
    void getAll_ShouldReturnListOfBooks() {
        Book book = new Book();
        List<Book> books = List.of(book);
        BookResponseDto responseDto = new BookResponseDto();

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toResponseDtoList(books)).thenReturn(List.of(responseDto));

        List<BookResponseDto> result = bookService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getAll_ShouldThrowDatabaseOperationException_WhenDataAccessExceptionOccurs() {
        when(bookRepository.findAll()).thenThrow(DatabaseOperationException.class);

        assertThrows(DatabaseOperationException.class, () -> bookService.getAll());
    }

    @Test
    void update_ShouldUpdateBookSuccessfully() {
        BookRequestDto requestDto = new BookRequestDto(1L, "Title", "Author", "ISBN",
                LocalDate.now(), "Category");
        Book book = new Book();
        BookResponseDto responseDto = new BookResponseDto();

        when(bookMapper.toEntity(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        BookResponseDto result = bookService.update(requestDto);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void update_ShouldThrowDatabaseOperationException_WhenDataAccessExceptionOccurs() {
        BookRequestDto requestDto = new BookRequestDto(1L, "Title", "Author", "ISBN",
                LocalDate.now(), "Category");

        when(bookMapper.toEntity(requestDto)).thenThrow(new DataIntegrityViolationException("Erro simulado"));

        assertThrows(DatabaseOperationException.class, () -> bookService.update(requestDto));
    }

    @Test
    void delete_ShouldDeleteBookSuccessfully() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(new Book()));

        bookService.delete(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowResourceNotFoundException_WhenBookNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.delete(1L));
    }

    @Test
    void getByIsbn_ShouldReturnBookSuccessfully() {
        String isbn = "1234567890";
        Book book = new Book();
        BookResponseDto responseDto = new BookResponseDto();

        when(bookRepository.findByIsbn(eq(isbn))).thenReturn(Optional.of(book));
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        BookResponseDto result = bookService.getByIsbn(isbn);

        assertNotNull(result);
        verify(bookRepository, times(1)).findByIsbn(isbn);
    }

    @Test
    void getByIsbn_ShouldThrowResourceNotFoundException_WhenBookNotFound() {
        String isbn = "1234567890";

        when(bookRepository.findByIsbn(eq(isbn))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getByIsbn(isbn));
    }

    @Test
    void getByTitle_ShouldReturnBookSuccessfully() {
        String title = "Book Title";
        Book book = new Book();
        BookResponseDto responseDto = new BookResponseDto();

        when(bookRepository.findByTitle(eq(title))).thenReturn(Optional.of(book));
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        BookResponseDto result = bookService.getByTitle(title);

        assertNotNull(result);
        verify(bookRepository, times(1)).findByTitle(title);
    }

    @Test
    void getByTitle_ShouldThrowResourceNotFoundException_WhenBookNotFound() {
        String title = "Book Title";

        when(bookRepository.findByTitle(eq(title))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getByTitle(title));
    }

    @Test
    void getById_ShouldReturnBookSuccessfully() {
        Long bookId = 1L;
        Book book = new Book();
        BookResponseDto responseDto = new BookResponseDto();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toResponseDto(book)).thenReturn(responseDto);

        BookResponseDto result = bookService.getById(bookId);

        assertNotNull(result);
        verify(bookRepository, times(1)).findById(bookId);
    }

    @Test
    void getById_ShouldThrowResourceNotFoundException_WhenBookNotFound() {
        Long bookId = 1L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookService.getById(bookId));
    }
}
