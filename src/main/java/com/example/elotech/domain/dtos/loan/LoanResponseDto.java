package com.example.elotech.domain.dtos.loan;


import com.example.elotech.domain.Book;
import com.example.elotech.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class LoanResponseDto {
    private Long id;
    private Users user;
    private Book book;
    private String status;
    private LocalDate loanDate;
}
