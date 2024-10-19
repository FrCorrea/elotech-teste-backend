package com.example.elotech.mappers;

import com.example.elotech.domain.Loan;
import com.example.elotech.domain.dtos.loan.LoanRequestDto;
import com.example.elotech.domain.dtos.loan.LoanResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    @Mapping(target = "id", source = "id")
    Loan toEntity(LoanRequestDto loanRequestDto);

    LoanResponseDto toResponseDto(Loan loan);

    List<LoanResponseDto> toResponseDtoList(List<Loan> loans);
}
