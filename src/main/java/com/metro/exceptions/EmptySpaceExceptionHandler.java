package com.metro.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmptySpaceExceptionHandler extends  RuntimeException{
    public  EmptySpaceExceptionHandler(String message){
        super(message);
    }
}
