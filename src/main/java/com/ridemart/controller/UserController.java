package com.ridemart.controller;

import com.ridemart.dto.UserDto;
import com.ridemart.dto.UserResponseDto;
import com.ridemart.entity.User;
import com.ridemart.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDto createUser(@RequestBody UserDto userDto) {
        return userService.saveUser(userDto);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getAuthenticatedUser() {
        UserResponseDto userDto = userService.getAuthenticatedUserDto();
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        User user = userService.getAuthenticatedUser();
        return ResponseEntity.ok(userService.updateUser(user.getId(), userDto));
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteUser() {
        User user = userService.getAuthenticatedUser();
        userService.deleteUser(user.getId());
        return ResponseEntity.status(204).body("User deleted successfully.");
    }
}
