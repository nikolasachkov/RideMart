package com.ridemart.controller;

import com.ridemart.dto.UserDto;
import com.ridemart.dto.UserResponseDto;
import com.ridemart.entity.User;
import com.ridemart.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserDto userDto) {
        log.info("Creating user: {}", userDto.getUsername());
        return ResponseEntity.ok(userService.saveUser(userDto));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        log.info("Fetching all users");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        log.info("Fetching user by ID: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getAuthenticatedUser() {
        log.info("Fetching authenticated user");
        UserResponseDto userDto = userService.getAuthenticatedUserDto();
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        User user = userService.getAuthenticatedUser();
        log.info("Updating authenticated user with ID {}", user.getId());
        return ResponseEntity.ok(userService.updateUser(user.getId(), userDto));
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser() {
        User user = userService.getAuthenticatedUser();
        log.info("Deleting authenticated user with ID {}", user.getId());
        userService.deleteUser(user.getId());
        return ResponseEntity.status(204).body("User deleted successfully.");
    }
}
