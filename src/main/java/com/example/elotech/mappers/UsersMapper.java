package com.example.elotech.mappers;


import com.example.elotech.domain.Users;
import com.example.elotech.domain.dtos.users.UsersRequestDto;
import com.example.elotech.domain.dtos.users.UsersResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    @Mapping(target = "id", source = "id")
    Users toEntity(UsersRequestDto usersRequestDto);
    UsersResponseDto toResponseDto(Users users);
    List<UsersResponseDto> toResponseDtoList(List<Users> users);

}
