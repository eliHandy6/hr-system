package com.metro.setups.titles.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TitleDto {
    @NotNull(message = "name is Missing")
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;

}
