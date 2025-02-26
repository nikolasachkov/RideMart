package com.ridemart.util;

public class ApiEndpoints {

    public static final String BASE_URL = "/api";

    // User Endpoints
    public static final String USER_BASE = BASE_URL + "/users";
    public static final String REGISTER = USER_BASE + "/register";
    public static final String LOGIN = USER_BASE + "/login";
    public static final String GET_USER_BY_ID = USER_BASE + "/{id}";

    // Advertisement Endpoints
    public static final String AD_BASE = BASE_URL + "/advertisements";
    public static final String GET_ALL_ADS = AD_BASE;
    public static final String GET_AD_BY_ID = AD_BASE + "/{id}";
    public static final String CREATE_AD = AD_BASE;
    public static final String UPDATE_AD = AD_BASE + "/{id}";
    public static final String DELETE_AD = AD_BASE + "/{id}";
}
