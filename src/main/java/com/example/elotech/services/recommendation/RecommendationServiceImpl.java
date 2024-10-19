package com.example.elotech.services.recommendation;


import com.example.elotech.domain.Book;
import com.example.elotech.domain.Users;
import com.example.elotech.domain.dtos.book.BookResponseDto;
import com.example.elotech.domain.dtos.loan.LoanResponseDto;
import com.example.elotech.domain.dtos.users.UsersResponseDto;
import com.example.elotech.exceptions.handlers.ResourceNotFoundException;
import com.example.elotech.mappers.BookMapper;
import com.example.elotech.services.books.BookService;
import com.example.elotech.services.loans.LoanService;
import com.example.elotech.services.users.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecommendationServiceImpl implements RecommendationService {

    private final BookService bookService;
    private final LoanService loanService;
    private final UserService userService;

    private final BookMapper bookMapper;

    public RecommendationServiceImpl(BookService bookService, LoanService loanService,
                                     UserService userService, BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.loanService = loanService;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookResponseDto> getRecommendations(Long userId) {
        try {
            this.userService.getById(userId);
            List<LoanResponseDto> loans = this.loanService.getByUserId(userId);
            if (loans.isEmpty()) {
                return this.bookService.getAll();
            }
            List<BookResponseDto> booksLoaned = bookMapper.toResponseDtoList(
                    loans.stream().map(LoanResponseDto::getBook).toList());

            List<BookResponseDto> allBooks = this.bookService.getAll();
            Set<String> loanedCategories = getCategories(booksLoaned);
            Set<Long> loanedBookIds = getLoanedBookIds(booksLoaned);

            return allBooks.stream()
                    .filter(book -> !loanedBookIds.contains(book.getId()))
                    .filter(book -> loanedCategories.contains(book.getCategory()))
                    .collect(Collectors.toList());

        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Erro ao obter recomendações", e);
        }
    }

    private Set<String> getCategories(List<BookResponseDto> booksLoaned) {
        return booksLoaned.stream()
                .map(BookResponseDto::getCategory)
                .collect(Collectors.toSet());
    }

    private Set<Long> getLoanedBookIds(List<BookResponseDto> booksLoaned) {
        return booksLoaned.stream()
                .map(BookResponseDto::getId)
                .collect(Collectors.toSet());
    }
}
