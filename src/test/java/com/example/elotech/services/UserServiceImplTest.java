package com.example.elotech.services;

import com.example.elotech.domain.Users;
import com.example.elotech.domain.dtos.users.UsersRequestDto;
import com.example.elotech.domain.dtos.users.UsersResponseDto;
import com.example.elotech.exceptions.handlers.DataAlreadyInUseException;
import com.example.elotech.exceptions.handlers.DatabaseOperationException;
import com.example.elotech.exceptions.handlers.ResourceNotFoundException;
import com.example.elotech.mappers.UsersMapper;
import com.example.elotech.repositories.UserRepository;
import com.example.elotech.services.users.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UsersMapper usersMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldSaveUserSuccessfully() {
        UsersRequestDto requestDto = new UsersRequestDto(null, "Romero", "romero.cor@example.com", LocalDateTime.now(), "123456789");
        Users user = new Users();
        UsersResponseDto responseDto = new UsersResponseDto();

        when(usersMapper.toEntity(requestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(usersMapper.toResponseDto(user)).thenReturn(responseDto);

        UsersResponseDto result = userService.save(requestDto);

        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void save_ShouldThrowDatabaseOperationException_WhenDataAccessExceptionOccurs() {
        UsersRequestDto requestDto = new UsersRequestDto(null, "Yuri Alberto", "yuri.cor@example.com", LocalDateTime.now(), "123456789");

        when(usersMapper.toEntity(requestDto)).thenThrow(new DataIntegrityViolationException("Erro simulado"));

        assertThrows(DatabaseOperationException.class, () -> userService.save(requestDto));
    }

    @Test
    void save_ShouldThrowDataAlreadyInUseException_WhenEmailIsAlreadyUsed() {
        UsersRequestDto requestDto = new UsersRequestDto(null, "Romero", "romero.cor@example.com", LocalDateTime.now(), "123456789");
        Users existingUser = new Users();

        when(userRepository.findByEmail(requestDto.email())).thenReturn(Optional.of(existingUser));

        assertThrows(DataAlreadyInUseException.class, () -> userService.save(requestDto));
    }

    @Test
    void update_ShouldUpdateUserSuccessfully() {
        UsersRequestDto requestDto = new UsersRequestDto(1L, "Romero", "romero.cor@example.com", LocalDateTime.now(), "123456789");
        Users user = new Users();
        UsersResponseDto responseDto = new UsersResponseDto();

        when(usersMapper.toEntity(requestDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(usersMapper.toResponseDto(user)).thenReturn(responseDto);

        UsersResponseDto result = userService.update(requestDto);

        assertNotNull(result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void update_ShouldThrowDatabaseOperationException_WhenDataAccessExceptionOccurs() {
        UsersRequestDto requestDto = new UsersRequestDto(1L, "Romero", "romero.cor@example.com", LocalDateTime.now(), "123456789");

        when(usersMapper.toEntity(requestDto)).thenThrow(new DataIntegrityViolationException("Erro simulado"));

        assertThrows(DatabaseOperationException.class, () -> userService.update(requestDto));
    }

    @Test
    void delete_ShouldDeleteUserSuccessfully() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new Users()));

        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.delete(1L));
    }

    @Test
    void getById_ShouldReturnUserSuccessfully() {
        Long userId = 1L;
        Users user = new Users();
        UsersResponseDto responseDto = new UsersResponseDto();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(usersMapper.toResponseDto(user)).thenReturn(responseDto);

        UsersResponseDto result = userService.getById(userId);

        assertNotNull(result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getById_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    void getByName_ShouldReturnUserSuccessfully() {
        String name = "John Doe";
        Users user = new Users();
        UsersResponseDto responseDto = new UsersResponseDto();

        when(userRepository.findByName(name)).thenReturn(Optional.of(user));
        when(usersMapper.toResponseDto(user)).thenReturn(responseDto);

        UsersResponseDto result = userService.getByName(name);

        assertNotNull(result);
        verify(userRepository, times(1)).findByName(name);
    }

    @Test
    void getByName_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        String name = "John Doe";

        when(userRepository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByName(name));
    }

    @Test
    void getByEmail_ShouldReturnUserSuccessfully() {
        String email = "john.doe@example.com";
        Users user = new Users();
        UsersResponseDto responseDto = new UsersResponseDto();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(usersMapper.toResponseDto(user)).thenReturn(responseDto);

        UsersResponseDto result = userService.getByEmail(email);

        assertNotNull(result);
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void getByEmail_ShouldThrowResourceNotFoundException_WhenUserNotFound() {
        String email = "john.doe@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getByEmail(email));
    }

    @Test
    void getAll_ShouldReturnListOfUsers() {
        Users user = new Users();
        List<Users> users = List.of(user);
        UsersResponseDto responseDto = new UsersResponseDto();

        when(userRepository.findAll()).thenReturn(users);
        when(usersMapper.toResponseDtoList(users)).thenReturn(List.of(responseDto));

        List<UsersResponseDto> result = userService.getAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getAll_ShouldThrowDatabaseOperationException_WhenDataAccessExceptionOccurs() {
        when(userRepository.findAll()).thenThrow(new DataIntegrityViolationException("Erro simulado"));

        assertThrows(DatabaseOperationException.class, () -> userService.getAll());
    }
}
