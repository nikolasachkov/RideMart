package com.ridemart.controller;

import com.ridemart.dto.AuthRequestDto;
import com.ridemart.dto.AuthResponseDto;
import com.ridemart.dto.RegisterRequestDto;
import com.ridemart.service.UserService;
import com.ridemart.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AuthController authController;

    private RegisterRequestDto testRegisterRequestDto;
    private AuthRequestDto testAuthRequestDto;
    private AuthResponseDto testAuthResponseDto;

    @BeforeEach
    void setUp() {
        testRegisterRequestDto = new RegisterRequestDto();
        testRegisterRequestDto.setUsername("testuser");
        testRegisterRequestDto.setEmail("test@example.com");
        testRegisterRequestDto.setPassword("password123");

        testAuthRequestDto = new AuthRequestDto();
        testAuthRequestDto.setUsername("testuser");
        testAuthRequestDto.setPassword("password123");

        testAuthResponseDto = new AuthResponseDto("test-token", "testuser");
    }

    @Test
    void register_ShouldReturnSuccessMessage() {
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<String> response = authController.register(testRegisterRequestDto, bindingResult);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("User registered successfully!", response.getBody());
        verify(userService).registerUser(testRegisterRequestDto);
    }

    @Test
    void login_ShouldReturnAuthResponse() {
        when(userService.verifyUserCredentials(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(anyString())).thenReturn("test-token");

        ResponseEntity<AuthResponseDto> response = authController.login(testAuthRequestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testAuthResponseDto.getToken(), response.getBody().getToken());
        assertEquals(testAuthResponseDto.getUsername(), response.getBody().getUsername());
        verify(userService).verifyUserCredentials(testAuthRequestDto.getUsername(), testAuthRequestDto.getPassword());
        verify(jwtUtil).generateToken(testAuthRequestDto.getUsername());
    }
} 