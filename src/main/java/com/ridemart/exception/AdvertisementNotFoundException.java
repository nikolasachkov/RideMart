package com.ridemart.exception;

public class AdvertisementNotFoundException extends RuntimeException {
    public AdvertisementNotFoundException(Integer id) {
        super("Advertisement with ID " + id + " not found");
    }
}