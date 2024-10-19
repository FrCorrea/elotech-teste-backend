package com.example.elotech.services.loans;

import com.example.elotech.domain.dtos.loan.LoanRequestDto;
import com.example.elotech.domain.dtos.loan.LoanResponseDto;

import java.util.List;

public interface LoanService {

    LoanResponseDto save(LoanRequestDto loansDto);

    LoanResponseDto returnLoan(LoanRequestDto loansDto);

    LoanResponseDto getById(Long id);

    List<LoanResponseDto> getByUserId(Long userId);

    List<LoanResponseDto> getByBookId(Long bookId);

    void delete(Long id);

}
