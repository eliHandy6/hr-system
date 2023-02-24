package com.metro.setups.shifdetails.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@AllArgsConstructor
public class ShiftDetailsDTO {
    @NotNull(message = "name is Missing")
    private String name;

    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;
}
