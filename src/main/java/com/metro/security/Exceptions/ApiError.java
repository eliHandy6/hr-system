package com.metro.security.Exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private String debugMessage;
    private String code;
    public ApiError() {

    }

    public ApiError(HttpStatus notFound, String format) {
        this();
        this.httpStatus = notFound;
        this.message = format;

    }
    public ApiError(HttpStatus status) {
        this();
        this.httpStatus = status;
        this.code = String.valueOf(status.value());
        this.message = status.getReasonPhrase();
    }


    public String getMessage() {
        return null;
    }
}
