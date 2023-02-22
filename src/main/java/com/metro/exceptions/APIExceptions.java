package com.metro.exceptions;

import org.springframework.http.HttpStatus;

import java.text.MessageFormat;

public class APIExceptions extends  RuntimeException{
    ApiError apiError;
    public APIExceptions(ApiError apiError) {
        super(apiError.getMessage());
        this.apiError = apiError;
    }
    public static APIExceptions notFound(final String message, final Object... args) {
        return new APIExceptions(
                new ApiError(HttpStatus.NOT_FOUND, MessageFormat.format(message, args))
        );
    }

    public static APIExceptions badRequest(final String message, final Object... args) {
        return new APIExceptions(
                new ApiError(HttpStatus.BAD_REQUEST, MessageFormat.format(message, args))
        );
    }

    public static APIExceptions internalError(final String message, final Object... args) {

        return new APIExceptions(
                new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, MessageFormat.format(message, args))
        );
    }
    public static  APIExceptions alreadyExists(final  String message, final Object... args){
        return new APIExceptions(
                new ApiError(
                        HttpStatus.CONFLICT, MessageFormat.format(message, args)
                )
        );
    }
}
