package com.example.elotech.services.loans;

import com.example.elotech.domain.Loan;
import com.example.elotech.domain.dtos.loan.LoanRequestDto;
import com.example.elotech.domain.dtos.loan.LoanResponseDto;
import com.example.elotech.exceptions.handlers.DatabaseOperationException;
import com.example.elotech.mappers.LoanMapper;
import com.example.elotech.repositories.LoanRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    private final LoanMapper loanMapper;

    public LoanServiceImpl(LoanRepository loanRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    @Override
    public LoanResponseDto save(LoanRequestDto loansDto) {
        try {
            checkIfBookIsAvailable(loansDto.bookId());
            Loan loan = loanMapper.toEntity(loansDto);
            loan.setLoanDate(LocalDate.now());
            loan = loanRepository.save(loan);
            return loanMapper.toResponseDto(loan);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar o registro de empréstimo");
        }
    }
    private void checkIfBookIsAvailable(Long bookId) {
        try {
            Optional<Loan> optionalLoan = this.loanRepository.findByIdAndStatus(bookId, "Pendente");
            optionalLoan.ifPresent(loan -> {
                throw new RuntimeException("Livro já está emprestado");
            });
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Erro ao verificar se o livro está disponível");
        }
    }
    @Override
    public LoanResponseDto returnLoan(LoanRequestDto loansDto) {
        try {
            Loan loan = loanMapper.toEntity(loansDto);
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
    public void delete(Long id) {
        try {
            if (this.loanRepository.findById(id).isEmpty()) {
                throw new RuntimeException("Empréstimo não encontrado");
            }
            this.loanRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Erro ao excluir o empréstimo");
        }
    }




}
