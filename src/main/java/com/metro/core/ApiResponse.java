package com.metro.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ApiResponse {

    private Boolean success;
    private String message;
    private Object data;
}
