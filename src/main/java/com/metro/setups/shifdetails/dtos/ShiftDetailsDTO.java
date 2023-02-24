package com.metro.setups.shifdetails.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShiftDetailsDTO {
    @NotNull(message = "name is Missing")
    private String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;
}
