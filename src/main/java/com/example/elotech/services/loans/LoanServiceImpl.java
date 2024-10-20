package com.example.elotech.services.loans;

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
import com.example.elotech.services.users.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final BookService bookService;
    private final UserService userService;
    private final BookMapper bookMapper;
    private final UsersMapper userMapper;

    public LoanServiceImpl(LoanRepository loanRepository, LoanMapper loanMapper, BookService bookService,
                           UserService userService, BookMapper bookMapper, UsersMapper userMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
        this.bookService = bookService;
        this.userService = userService;
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
    }

    @Override
    public LoanResponseDto save(LoanRequestDto loansDto) {
        checkIfBookIsAvailable(loansDto.bookId());
        try {
            Loan loan = loanMapper.toEntity(loansDto);
            loan.setUser(userMapper.toEntity(userService.getById(loansDto.userId())));
            loan.setBook(bookMapper.toEntity(bookService.getById(loansDto.bookId())));
            loan.setLoanDate(LocalDate.now());
            loan.setStatus("Pendente");
            loan = loanRepository.save(loan);
            return loanMapper.toResponseDto(loan);
        } catch (Exception e) {
            throw new DatabaseOperationException("Erro ao salvar o registro de empréstimo");
        }
    }
    private void checkIfBookIsAvailable(Long bookId) {
        Optional<Loan> optionalLoan = this.loanRepository.findByIdAndStatus(bookId, "Pendente");
        optionalLoan.ifPresent(loan -> {
            throw new DataAlreadyInUseException("O email já está sendo usado");
        });
    }
    @Override
    public LoanResponseDto returnLoan(LoanRequestDto loansDto) {
        try {
            Loan loan = loanMapper.toEntity(loansDto);
            loan.setUser(userMapper.toEntity(userService.getById(loansDto.userId())));
            loan.setBook(bookMapper.toEntity(bookService.getById(loansDto.bookId())));
            loan.setStatus("Retornado");
            loan = loanRepository.save(loan);
            return loanMapper.toResponseDto(loan);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Erro ao atualizar o registro de empréstimo");
        }
    }

    @Override
    public LoanResponseDto getById(Long id) {
        try {
            Optional<Loan> optionalLoan = this.loanRepository.findById(id);
            Loan loan = optionalLoan.orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
            return loanMapper.toResponseDto(loan);
        }catch (DataAccessException e){
            throw new DatabaseOperationException("Erro ao buscar o empréstimo");
        }
    }

    @Override
    public List<LoanResponseDto> getByUserId(Long userId) {
        try {
            return loanMapper.toResponseDtoList(this.loanRepository.findByUserId(userId));
        }catch (DataAccessException e){
            throw new DatabaseOperationException("Erro ao buscar o empréstimo");
        }
    }

    @Override
    public List<LoanResponseDto> getByBookId(Long bookId) {
        try{
            return loanMapper.toResponseDtoList(this.loanRepository.findByBookId(bookId));
        } catch (DataAccessException e){
                throw new DatabaseOperationException("Erro ao buscar o empréstimo");
            }
    }

    @Override
    public List<LoanResponseDto> getAll() {
        try{
            return loanMapper.toResponseDtoList(this.loanRepository.findAll());
        } catch (DataAccessException e){
            throw new DatabaseOperationException("Erro ao buscar o empréstimo");
        }
    }
}
