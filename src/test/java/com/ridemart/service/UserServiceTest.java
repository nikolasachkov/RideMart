package com.ridemart.service;

import com.ridemart.dto.RegisterRequestDto;
import com.ridemart.dto.UserDto;
import com.ridemart.dto.UserResponseDto;
import com.ridemart.entity.User;
import com.ridemart.mapper.UserMapper;
import com.ridemart.repository.UserRepository;
import com.ridemart.util.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserDto testUserDto;
    private UserResponseDto testUserResponseDto;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPhoneNumber("1234567890");
        testUser.setPassword("$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");
        testUserDto = new UserDto();
        testUserDto.setUsername("testuser");
        testUserDto.setEmail("test@example.com");
        testUserDto.setPhoneNumber("1234567890");
        testUserResponseDto = new UserResponseDto();
        testUserResponseDto.setId(1);
        testUserResponseDto.setUsername("testuser");
        testUserResponseDto.setEmail("test@example.com");
        testUserResponseDto.setPhoneNumber("1234567890");
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        List<User> users = Arrays.asList(testUser);
        List<UserResponseDto> expectedDtos = Arrays.asList(testUserResponseDto);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toResponseDtoList(users)).thenReturn(expectedDtos);
        List<UserResponseDto> result = userService.getAllUsers();
        assertNotNull(result);
        assertEquals(expectedDtos.size(), result.size());
        verify(userRepository).findAll();
        verify(userMapper).toResponseDtoList(users);
    }

    @Test
    void saveUser_ShouldReturnSavedUser() {
        when(userMapper.toEntity(testUserDto)).thenReturn(testUser);
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(userMapper.toResponseDto(testUser)).thenReturn(testUserResponseDto);
        UserResponseDto result = userService.saveUser(testUserDto);
        assertNotNull(result);
        assertEquals(testUserResponseDto.getUsername(), result.getUsername());
        verify(userMapper).toEntity(testUserDto);
        verify(userRepository).save(testUser);
        verify(userMapper).toResponseDto(testUser);
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userMapper.toResponseDto(testUser)).thenReturn(testUserResponseDto);
        UserResponseDto result = userService.getUserById(1);
        assertNotNull(result);
        assertEquals(testUserResponseDto.getUsername(), result.getUsername());
        verify(userRepository).findById(1);
        verify(userMapper).toResponseDto(testUser);
    }

    @Test
    void getAuthenticatedUserDto_ShouldReturnCurrentUser() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(userMapper.toResponseDto(testUser)).thenReturn(testUserResponseDto);
        UserResponseDto result = userService.getAuthenticatedUserDto();
        assertNotNull(result);
        assertEquals(testUserResponseDto.getUsername(), result.getUsername());
        verify(userRepository).findByUsername("testuser");
        verify(userMapper).toResponseDto(testUser);
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userMapper.toResponseDto(testUser)).thenReturn(testUserResponseDto);
        UserResponseDto result = userService.updateUser(1, testUserDto);
        assertNotNull(result);
        assertEquals(testUserResponseDto.getUsername(), result.getUsername());
        verify(userRepository).findById(1);
        verify(userRepository).save(any(User.class));
        verify(userMapper).toResponseDto(testUser);
    }

    @Test
    void registerUser_ShouldSaveNewUser() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername("newuser");
        registerRequestDto.setEmail("new@example.com");
        registerRequestDto.setPassword("password123");
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(userMapper.toEntity(registerRequestDto)).thenReturn(testUser);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        userService.registerUser(registerRequestDto);
        verify(userRepository).findByUsername("newuser");
        verify(userRepository).findByEmail("new@example.com");
        verify(userMapper).toEntity(registerRequestDto);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        userService.deleteUser(1);
        verify(userRepository).findById(1);
        verify(userRepository).delete(testUser);
    }

    @Test
    void verifyUserCredentials_ShouldReturnTrueForValidCredentials() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        try (MockedStatic<PasswordUtil> utilities = mockStatic(PasswordUtil.class)) {
            utilities.when(() -> PasswordUtil.verifyPassword("password123", user.getPassword())).thenReturn(true);
            boolean result = userService.verifyUserCredentials("testuser", "password123");
            assertTrue(result);
            verify(userRepository).findByUsername("testuser");
            utilities.verify(() -> PasswordUtil.verifyPassword("password123", user.getPassword()));
        }
    }

    @Test
    void getUserById_ShouldThrowWhenUserNotFound() {
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserById(2));
        verify(userRepository).findById(2);
    }

    @Test
    void updateUser_ShouldThrowWhenUserNotFound() {
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.updateUser(2, testUserDto));
        verify(userRepository).findById(2);
    }

    @Test
    void deleteUser_ShouldThrowWhenUserNotFound() {
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.deleteUser(2));
        verify(userRepository).findById(2);
    }

    @Test
    void registerUser_ShouldThrowWhenUsernameExists() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername("testuser");
        registerRequestDto.setEmail("new@example.com");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        assertThrows(RuntimeException.class, () -> userService.registerUser(registerRequestDto));
        verify(userRepository).findByUsername("testuser");
    }

    @Test
    void registerUser_ShouldThrowWhenEmailExists() {
        RegisterRequestDto registerRequestDto = new RegisterRequestDto();
        registerRequestDto.setUsername("newuser");
        registerRequestDto.setEmail("test@example.com");
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        assertThrows(RuntimeException.class, () -> userService.registerUser(registerRequestDto));
        verify(userRepository).findByUsername("newuser");
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void verifyUserCredentials_ShouldThrowWhenUserNotFound() {
        when(userRepository.findByUsername("nouser")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.verifyUserCredentials("nouser", "password123"));
        verify(userRepository).findByUsername("nouser");
    }

    @Test
    void verifyUserCredentials_ShouldThrowWhenPasswordInvalid() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        try (MockedStatic<PasswordUtil> utilities = mockStatic(PasswordUtil.class)) {
            utilities.when(() -> PasswordUtil.verifyPassword("wrongpass", user.getPassword())).thenReturn(false);
            assertThrows(RuntimeException.class, () -> userService.verifyUserCredentials("testuser", "wrongpass"));
            verify(userRepository).findByUsername("testuser");
            utilities.verify(() -> PasswordUtil.verifyPassword("wrongpass", user.getPassword()));
        }
    }

    @Test
    void getAuthenticatedUserDto_ShouldThrowWhenUserNotFound() {
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("nouser");
        when(userRepository.findByUsername("nouser")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getAuthenticatedUserDto());
        verify(userRepository).findByUsername("nouser");
    }
}
 