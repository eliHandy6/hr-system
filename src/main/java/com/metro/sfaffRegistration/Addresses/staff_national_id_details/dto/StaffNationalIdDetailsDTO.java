package com.metro.sfaffRegistration.Addresses.staff_national_id_details.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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
public class StaffNationalIdDetailsDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;

    private String division;
    private String location;
    private Long district_id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long employee_id;
}
