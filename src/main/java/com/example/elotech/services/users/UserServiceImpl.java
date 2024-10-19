package com.example.elotech.services.users;


import com.example.elotech.domain.Users;
import com.example.elotech.domain.dtos.users.UsersRequestDto;
import com.example.elotech.domain.dtos.users.UsersResponseDto;
import com.example.elotech.exceptions.handlers.DataAlreadyInUseException;
import com.example.elotech.exceptions.handlers.DatabaseOperationException;
import com.example.elotech.exceptions.handlers.ResourceNotFoundException;
import com.example.elotech.mappers.UsersMapper;
import com.example.elotech.repositories.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UsersMapper usersMapper;

    public UserServiceImpl(UserRepository userRepository, UsersMapper usersMapper) {
        this.userRepository = userRepository;
        this.usersMapper = usersMapper;
    }


    @Override
    public UsersResponseDto save(UsersRequestDto usersDto) {
        try {
            checkEmailHasBeenUsed(usersDto.email());
            Users users = usersMapper.toEntity(usersDto);
            users = userRepository.save(users);
            return usersMapper.toResponseDto(users);
        }catch (DataAccessException e){
            throw new DatabaseOperationException("Erro ao salvar o usuário");
        }
    }
    public void checkEmailHasBeenUsed(String email) {
        Optional<Users> optionalUsers = this.userRepository.findByEmail(email);
        optionalUsers.ifPresent(users -> {
            throw new DataAlreadyInUseException("O email já está sendo usado");
        });
    }
    @Override
    public UsersResponseDto update(UsersRequestDto usersDto) {
        try {
            Users users = usersMapper.toEntity(usersDto);
            checkEmailHasBeenUsed(users.getEmail());
            users = userRepository.save(users);
            return usersMapper.toResponseDto(users);
        }catch (DataAccessException e){
            throw new DatabaseOperationException("Erro ao atualizar o usuário");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (this.userRepository.findById(id).isEmpty()) {
                throw new ResourceNotFoundException("Usuário não encontrado");
            }
            this.userRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Erro ao excluir o usuário");
        }
    }

    @Override
    public UsersResponseDto getById(Long id) {
        try{
            Optional<Users> optionalUser = this.userRepository.findById(id);
            Users user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            return usersMapper.toResponseDto(user);
        }catch (DataAccessException e){
            throw new DatabaseOperationException("Erro ao buscar o usuário");
        }
    }

    @Override
    public UsersResponseDto getByName(String name) {
        try{
            Optional<Users> optionalUser = this.userRepository.findByName(name);
            Users user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            return usersMapper.toResponseDto(user);
        }catch (DataAccessException e){
            throw new DatabaseOperationException("Erro ao buscar o usuário");
        }
    }

    @Override
    public UsersResponseDto getByEmail(String email) {
        try{
            Optional<Users> optionalUser = this.userRepository.findByEmail(email);
            Users user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
            return usersMapper.toResponseDto(user);
        }catch (DataAccessException e){
            throw new DatabaseOperationException("Erro ao buscar o usuário");
        }
    }

    @Override
    public List<UsersResponseDto> getAll() {
        try{
            List<Users> users = this.userRepository.findAll();
            return usersMapper.toResponseDtoList(users);
        }catch (DataAccessException e){
            throw new DatabaseOperationException("Erro ao buscar os usuários");
        }
    }
}
