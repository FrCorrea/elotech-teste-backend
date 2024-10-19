package com.example.elotech.domain.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersResponseDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime registerDate;
    private String phoneNumber;
}
