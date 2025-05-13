package com.ridemart.service;

import com.ridemart.dto.RegisterRequestDto;
import com.ridemart.dto.UserDto;
import com.ridemart.dto.UserResponseDto;
import com.ridemart.entity.User;
import com.ridemart.exception.UserAlreadyExistsException;
import com.ridemart.exception.InvalidCredentialsException;
import com.ridemart.exception.UserNotFoundException;
import com.ridemart.mapper.UserMapper;
import com.ridemart.repository.UserRepository;
import com.ridemart.util.PasswordUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDto> getAllUsers() {
        log.info("Fetching all users");
        return userMapper.toResponseDtoList(userRepository.findAll());
    }

    public UserResponseDto saveUser(UserDto userDto) {
        log.info("Saving user: {}", userDto.getUsername());
        User user = userMapper.toEntity(userDto);
        return userMapper.toResponseDto(userRepository.save(user));
    }

    public UserResponseDto getUserById(Integer id) {
        log.info("Fetching user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponseDto(user);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UserNotFoundException(authentication.getName()));
    }

    public UserResponseDto getAuthenticatedUserDto() {
        log.info("Fetching authenticated user DTO");
        return userMapper.toResponseDto(getAuthenticatedUser());
    }

    public UserResponseDto updateUser(Integer id, UserDto userDto) {
        log.info("Updating user with ID {}", id);
        User updatedUser = userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUsername(userDto.getUsername());
                    existingUser.setEmail(userDto.getEmail());
                    existingUser.setPhoneNumber(userDto.getPhoneNumber());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toResponseDto(updatedUser);
    }

    public void deleteUser(Integer id) {
        log.info("Deleting user with ID {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equals(user.getUsername())) {
            throw new AccessDeniedException("You can only delete your own account.");
        }

        userRepository.delete(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public boolean verifyUserCredentials(String username, String password) {
        log.info("Verifying credentials for {}", username);
        User user = findByUsername(username);
        if (!PasswordUtil.verifyPassword(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }
        return true;
    }

    public Integer getUserIdFromUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username))
                .getId();
    }

    public void registerUser(RegisterRequestDto registerRequestDto) {
        log.info("Registering new user {}", registerRequestDto.getUsername());
        if (userRepository.findByUsername(registerRequestDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username");
        }
        if (userRepository.findByEmail(registerRequestDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email");
        }
        User user = userMapper.toEntity(registerRequestDto);
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        userRepository.save(user);
    }
}