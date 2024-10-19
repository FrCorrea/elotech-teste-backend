package com.example.elotech.controllers;


import com.example.elotech.domain.dtos.loan.LoanRequestDto;
import com.example.elotech.domain.dtos.loan.LoanResponseDto;
import com.example.elotech.services.loans.LoanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/save")
    public LoanResponseDto saveLoan(@RequestBody @Valid LoanRequestDto loansDto) {
        return this.loanService.save(loansDto);
    }

    @PutMapping("/return")
    public LoanResponseDto returnLoan(@RequestBody @Valid LoanRequestDto loansDto) {
        return this.loanService.returnLoan(loansDto);
    }

    @GetMapping("/getById/{id}")
    public LoanResponseDto getById(@PathVariable @Valid @NotNull(message = "O id é obrigatório")
                                       Long id) {
        return this.loanService.getById(id);
    }

    @GetMapping("/getByUserId/{userId}")
    public List<LoanResponseDto> getByUserId(@PathVariable @Valid @NotNull(message = "O id é obrigatório")
                                                 Long userId) {
        return this.loanService.getByUserId(userId);
    }

    @GetMapping("/getByBookId/{bookId}")
    public List<LoanResponseDto> getByBookId(@PathVariable @Valid @NotNull(message = "O id é obrigatório")
                                                 Long bookId) {
        return this.loanService.getByBookId(bookId);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable @Valid @NotNull(message = "O id é obrigatório") Long id) {
        this.loanService.delete(id);
    }
}
