package com.metro.exceptions;

import lombok.Data;

@Data
public class ApiResponse {
    Boolean success;
    String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;

    }
}
