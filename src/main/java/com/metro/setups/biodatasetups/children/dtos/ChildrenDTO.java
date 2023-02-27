package com.metro.setups.biodatasetups.children.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.metro.setups.biodatasetups.children.specifications.Gender;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
public class ChildrenDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;
    @NotNull(message = "First name missing")
    private String firstName;
    @NotNull(message = "Second name missing")
    private String secondName;
    @NotNull(message = "Third name missing")
    private String lastName;
    @NotNull(message = "Date of birth is missing")
    private LocalDate dateOfBirth;
    @NotNull(message = "Gender is missing")
    private Gender gender;
    private Long employee_id;

}
