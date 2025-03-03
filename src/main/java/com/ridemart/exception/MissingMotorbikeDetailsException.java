package com.ridemart.exception;

public class MissingMotorbikeDetailsException extends RuntimeException {
    public MissingMotorbikeDetailsException() {
        super("Motorbike details are required for an advertisement.");
    }
}
