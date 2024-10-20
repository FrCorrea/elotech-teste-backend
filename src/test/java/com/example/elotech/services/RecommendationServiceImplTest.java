package com.example.elotech.services;

import com.example.elotech.domain.Book;
import com.example.elotech.domain.dtos.book.BookResponseDto;
import com.example.elotech.domain.dtos.loan.LoanResponseDto;
import com.example.elotech.exceptions.handlers.ResourceNotFoundException;
import com.example.elotech.mappers.BookMapper;
import com.example.elotech.services.books.BookService;
import com.example.elotech.services.loans.LoanService;
import com.example.elotech.services.recommendation.RecommendationServiceImpl;
import com.example.elotech.services.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecommendationServiceImplTest {

    @Mock
    private BookService bookService;

    @Mock
    private LoanService loanService;

    @Mock
    private UserService userService;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRecommendations_ShouldReturnAllBooks_WhenUserHasNoLoans() {
        Long userId = 1L;
        List<BookResponseDto> allBooks = List.of(new BookResponseDto());

        when(userService.getById(userId)).thenReturn(null); // Simula que o usuário existe
        when(loanService.getByUserId(userId)).thenReturn(List.of());
        when(bookService.getAll()).thenReturn(allBooks);

        List<BookResponseDto> result = recommendationService.getRecommendations(userId);

        assertNotNull(result);
        assertEquals(allBooks.size(), result.size());
        verify(bookService, times(1)).getAll();
    }

    @Test
    void getRecommendations_ShouldReturnFilteredBooks_WhenUserHasLoans() {
        Long userId = 1L;
        LoanResponseDto loan = new LoanResponseDto();
        BookResponseDto loanedBook = new BookResponseDto(1L, "Title", "Author", "Category", "ISBN");
        loan.setBook(new Book());

        List<LoanResponseDto> loans = List.of(loan);
        List<BookResponseDto> allBooks = List.of(
                new BookResponseDto(1L, "Title", "Author", "Category", "ISBN"),
                new BookResponseDto(2L, "Another Title", "Another Author", "Category", "ISBN2")
        );
        List<BookResponseDto> loanedBooks = List.of(loanedBook);

        when(userService.getById(userId)).thenReturn(null); // Simula que o usuário existe
        when(loanService.getByUserId(userId)).thenReturn(loans);
        when(bookMapper.toResponseDtoList(loans.stream().map(LoanResponseDto::getBook).toList())).thenReturn(loanedBooks);
        when(bookService.getAll()).thenReturn(allBooks);

        List<BookResponseDto> result = recommendationService.getRecommendations(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getId());
    }

    @Test
    void getRecommendations_ShouldThrowRuntimeException_WhenUserNotFound() {
        Long userId = 1L;

        doThrow(new ResourceNotFoundException("Usuário não encontrado")).when(userService).getById(userId);

        assertThrows(RuntimeException.class, () -> recommendationService.getRecommendations(userId));
    }
}
