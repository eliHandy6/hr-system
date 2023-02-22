package com.metro.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class ApiResponses {
    private Boolean success;
    private String message;
    private Object data;

    public ApiResponses(boolean success, String message){
        this.success = success;
        this.message = message;
    }


}
