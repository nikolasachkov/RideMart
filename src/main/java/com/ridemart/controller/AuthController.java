package com.ridemart.controller;

import com.ridemart.dto.AuthRequestDto;
import com.ridemart.dto.AuthResponseDto;
import com.ridemart.dto.RegisterRequestDto;
import com.ridemart.exception.ValidationException;
import com.ridemart.service.UserService;
import jakarta.validation.Valid;
import com.ridemart.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDto registerRequestDto,  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error ->
                    errors.put(error.getField(), error.getDefaultMessage())
            );
            throw new ValidationException(errors);
        }


        userService.registerUser(registerRequestDto);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto) {
        userService.verifyUserCredentials(authRequestDto.getUsername(), authRequestDto.getPassword());

        return ResponseEntity.ok(new AuthResponseDto(
                jwtUtil.generateToken(authRequestDto.getUsername()),
                authRequestDto.getUsername()
        ));
    }
}