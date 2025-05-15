package com.ridemart.controller;

import com.ridemart.dto.UserDto;
import com.ridemart.dto.UserResponseDto;
import com.ridemart.entity.User;
import com.ridemart.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDto testUserDto;
    private UserResponseDto testResponseDto;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUserDto = new UserDto();
        testUserDto.setUsername("testuser");
        testUserDto.setEmail("test@example.com");
        testUserDto.setPhoneNumber("1234567890");

        testResponseDto = new UserResponseDto();
        testResponseDto.setId(1);
        testResponseDto.setUsername("testuser");
        testResponseDto.setEmail("test@example.com");
        testResponseDto.setPhoneNumber("1234567890");

        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPhoneNumber("1234567890");
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        when(userService.saveUser(any(UserDto.class))).thenReturn(testResponseDto);

        ResponseEntity<UserResponseDto> response = userController.createUser(testUserDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResponseDto, response.getBody());
        verify(userService).saveUser(testUserDto);
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        List<UserResponseDto> expectedDtos = Arrays.asList(testResponseDto);
        when(userService.getAllUsers()).thenReturn(expectedDtos);

        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedDtos, response.getBody());
        verify(userService).getAllUsers();
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userService.getUserById(1)).thenReturn(testResponseDto);

        ResponseEntity<?> response = userController.getUserById(1);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResponseDto, response.getBody());
        verify(userService).getUserById(1);
    }

    @Test
    void getAuthenticatedUser_ShouldReturnCurrentUser() {
        when(userService.getAuthenticatedUserDto()).thenReturn(testResponseDto);

        ResponseEntity<UserResponseDto> response = userController.getAuthenticatedUser();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResponseDto, response.getBody());
        verify(userService).getAuthenticatedUserDto();
    }

    @Test
    void updateUser_ShouldReturnUpdatedUser() {
        when(userService.getAuthenticatedUser()).thenReturn(testUser);
        when(userService.updateUser(anyInt(), any(UserDto.class))).thenReturn(testResponseDto);

        ResponseEntity<?> response = userController.updateUser(testUserDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testResponseDto, response.getBody());
        verify(userService).getAuthenticatedUser();
        verify(userService).updateUser(testUser.getId(), testUserDto);
    }

    @Test
    void deleteUser_ShouldReturnNoContent() {
        when(userService.getAuthenticatedUser()).thenReturn(testUser);

        ResponseEntity<?> response = userController.deleteUser();

        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
        assertEquals("User deleted successfully.", response.getBody());
        verify(userService).getAuthenticatedUser();
        verify(userService).deleteUser(testUser.getId());
    }

    @Test
    void getUserById_ShouldHandleUserNotFound() {
        when(userService.getUserById(999)).thenReturn(null);
        ResponseEntity<?> response = userController.getUserById(999);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userService).getUserById(999);
    }

    @Test
    void getAllUsers_ShouldReturnEmptyList() {
        when(userService.getAllUsers()).thenReturn(Arrays.asList());
        ResponseEntity<List<UserResponseDto>> response = userController.getAllUsers();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
        verify(userService).getAllUsers();
    }

    @Test
    void createUser_ShouldHandleNullReturn() {
        when(userService.saveUser(any(UserDto.class))).thenReturn(null);
        ResponseEntity<UserResponseDto> response = userController.createUser(testUserDto);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(userService).saveUser(testUserDto);
    }

    @Test
    void updateUser_ShouldHandleNullAuthenticatedUser() {
        when(userService.getAuthenticatedUser()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> userController.updateUser(testUserDto));
        verify(userService).getAuthenticatedUser();
    }

    @Test
    void deleteUser_ShouldHandleNullAuthenticatedUser() {
        when(userService.getAuthenticatedUser()).thenReturn(null);
        assertThrows(NullPointerException.class, () -> userController.deleteUser());
        verify(userService).getAuthenticatedUser();
    }

    @Test
    void updateUser_ShouldHandleException() {
        when(userService.getAuthenticatedUser()).thenReturn(testUser);
        when(userService.updateUser(anyInt(), any(UserDto.class))).thenThrow(new RuntimeException("Update failed"));
        assertThrows(RuntimeException.class, () -> userController.updateUser(testUserDto));
        verify(userService).getAuthenticatedUser();
        verify(userService).updateUser(testUser.getId(), testUserDto);
    }

    @Test
    void deleteUser_ShouldHandleException() {
        when(userService.getAuthenticatedUser()).thenReturn(testUser);
        doThrow(new RuntimeException("Delete failed")).when(userService).deleteUser(testUser.getId());
        assertThrows(RuntimeException.class, () -> userController.deleteUser());
        verify(userService).getAuthenticatedUser();
        verify(userService).deleteUser(testUser.getId());
    }
} 