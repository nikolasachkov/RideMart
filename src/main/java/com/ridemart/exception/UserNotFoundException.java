package com.ridemart.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("User with ID " + id + " not found");
    }

    public UserNotFoundException(String username) {
        super("User with username " + username + " not found");
    }
}
