package com.metro.setups.biodatasetups.spouse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
public class SpouseDetailsDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;

    @NotNull(message = "Name missing")
    private String name;
    @NotNull(message = "Phone Number is missing")
    private String phoneNumber;
    @NotNull(message = "email is missing")
    private String email;
    @NotNull(message = "Residence is missing")
    private String Residence;
    @NotNull(message = "National ID is missing")
    private Long nationalID;
    private Long employee_ID;
}
