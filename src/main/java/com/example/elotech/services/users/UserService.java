package com.example.elotech.services.users;

import com.example.elotech.domain.dtos.users.UsersRequestDto;
import com.example.elotech.domain.dtos.users.UsersResponseDto;

import java.util.List;

public interface UserService {

    UsersResponseDto save(UsersRequestDto usersDto);

    UsersResponseDto update(UsersRequestDto usersDto);

    void delete(Long id);

    UsersResponseDto getById(Long id);

    UsersResponseDto getByName(String name);

    UsersResponseDto getByEmail(String email);

    List<UsersResponseDto> getAll();
}
