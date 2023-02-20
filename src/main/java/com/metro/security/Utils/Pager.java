package com.metro.security.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Pager<T> {
    private String code;
    private String message;
    private T content;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PageDetails pageDetails;

}
