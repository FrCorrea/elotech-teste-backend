package com.example.elotech.services;

import com.example.elotech.domain.Loan;
import com.example.elotech.domain.dtos.loan.LoanRequestDto;
import com.example.elotech.domain.dtos.loan.LoanResponseDto;
import com.example.elotech.exceptions.handlers.DataAlreadyInUseException;
import com.example.elotech.exceptions.handlers.DatabaseOperationException;
import com.example.elotech.mappers.BookMapper;
import com.example.elotech.mappers.LoanMapper;
import com.example.elotech.mappers.UsersMapper;
import com.example.elotech.repositories.LoanRepository;
import com.example.elotech.services.books.BookService;
import com.example.elotech.services.loans.LoanServiceImpl;
import com.example.elotech.services.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanMapper loanMapper;

    @Mock
    private BookService bookService;

    @Mock
    private UserService userService;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private UsersMapper userMapper;

    @InjectMocks
    private LoanServiceImpl loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldSaveLoanSuccessfully() {
        LoanRequestDto requestDto = new LoanRequestDto(null, 1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7), "Pendente");
        Loan loan = new Loan();
        LoanResponseDto responseDto = new LoanResponseDto();

        when(loanMapper.toEntity(requestDto)).thenReturn(loan);
        when(userService.getById(requestDto.userId())).thenReturn(any());
        when(bookService.getById(requestDto.bookId())).thenReturn(any());
        when(loanRepository.save(loan)).thenReturn(loan);
        when(loanMapper.toResponseDto(loan)).thenReturn(responseDto);

        LoanResponseDto result = loanService.save(requestDto);

        assertNotNull(result);
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void save_ShouldThrowDataAlreadyInUseException_WhenBookAlreadyLoaned() {
        Long bookId = 1L;
        LoanRequestDto requestDto = new LoanRequestDto(null, 1L, bookId, LocalDate.now(), LocalDate.now().plusDays(7), "Pendente");

        when(loanRepository.findByIdAndStatus(bookId, "Pendente")).thenReturn(Optional.of(new Loan()));

        assertThrows(DataAlreadyInUseException.class, () -> loanService.save(requestDto));
    }

    @Test
    void save_ShouldThrowDatabaseOperationException_WhenDataAccessExceptionOccurs() {
        LoanRequestDto requestDto = new LoanRequestDto(null, 1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7), "Pendente");

        when(loanMapper.toEntity(requestDto)).thenThrow(new DataIntegrityViolationException("Erro simulado"));

        assertThrows(DatabaseOperationException.class, () -> loanService.save(requestDto));
    }

    @Test
    void returnLoan_ShouldReturnLoanSuccessfully() {
        LoanRequestDto requestDto = new LoanRequestDto(1L, 1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7), "Retornado");
        Loan loan = new Loan();
        LoanResponseDto responseDto = new LoanResponseDto();

        when(loanMapper.toEntity(requestDto)).thenReturn(loan);
        when(userService.getById(requestDto.userId())).thenReturn(any());
        when(bookService.getById(requestDto.bookId())).thenReturn(any());
        when(loanRepository.save(loan)).thenReturn(loan);
        when(loanMapper.toResponseDto(loan)).thenReturn(responseDto);

        LoanResponseDto result = loanService.returnLoan(requestDto);

        assertNotNull(result);
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void returnLoan_ShouldThrowDatabaseOperationException_WhenDataAccessExceptionOccurs() {
        LoanRequestDto requestDto = new LoanRequestDto(1L, 1L, 1L, LocalDate.now(), LocalDate.now().plusDays(7), "Retornado");

        when(loanMapper.toEntity(requestDto)).thenThrow(new DataIntegrityViolationException("Erro simulado"));

        assertThrows(DatabaseOperationException.class, () -> loanService.returnLoan(requestDto));
    }

    @Test
    void getById_ShouldReturnLoanSuccessfully() {
        Long loanId = 1L;
        Loan loan = new Loan();
        LoanResponseDto responseDto = new LoanResponseDto();

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanMapper.toResponseDto(loan)).thenReturn(responseDto);

        LoanResponseDto result = loanService.getById(loanId);

        assertNotNull(result);
        verify(loanRepository, times(1)).findById(loanId);
    }

    @Test
    void getById_ShouldThrowDatabaseOperationException_WhenDataAccessExceptionOccurs() {
        Long loanId = 1L;

        when(loanRepository.findById(loanId)).thenThrow(DatabaseOperationException.class);

        assertThrows(DatabaseOperationException.class, () -> loanService.getById(loanId));
    }

    @Test
    void getByUserId_ShouldReturnLoansSuccessfully() {
        Long userId = 1L;
        Loan loan = new Loan();
        List<Loan> loans = List.of(loan);
        LoanResponseDto responseDto = new LoanResponseDto();

        when(loanRepository.findByUserId(userId)).thenReturn(loans);
        when(loanMapper.toResponseDtoList(loans)).thenReturn(List.of(responseDto));

        List<LoanResponseDto> result = loanService.getByUserId(userId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(loanRepository, times(1)).findByUserId(userId);
    }

    @Test
    void getByBookId_ShouldReturnLoansSuccessfully() {
        Long bookId = 1L;
        Loan loan = new Loan();
        List<Loan> loans = List.of(loan);
        LoanResponseDto responseDto = new LoanResponseDto();

        when(loanRepository.findByBookId(bookId)).thenReturn(loans);
        when(loanMapper.toResponseDtoList(loans)).thenReturn(List.of(responseDto));

        List<LoanResponseDto> result = loanService.getByBookId(bookId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(loanRepository, times(1)).findByBookId(bookId);
    }

    @Test
    void getAll_ShouldReturnListOfLoans() {
        Loan loan = new Loan();
        List<Loan> loans = List.of(loan);
        LoanResponseDto responseDto = new LoanResponseDto();

        when(loanRepository.findAll()).thenReturn(loans);
        when(loanMapper.toResponseDtoList(loans)).thenReturn(List.of(responseDto));

        List<LoanResponseDto> result = loanService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    void getAll_ShouldThrowDatabaseOperationException_WhenDataAccessExceptionOccurs() {
        when(loanRepository.findAll()).thenThrow(DatabaseOperationException.class);

        assertThrows(DatabaseOperationException.class, () -> loanService.getAll());
    }
}
