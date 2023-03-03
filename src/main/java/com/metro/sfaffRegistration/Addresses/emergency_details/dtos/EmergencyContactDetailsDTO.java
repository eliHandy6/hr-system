package com.metro.sfaffRegistration.Addresses.emergency_details.dtos;

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
public class EmergencyContactDetailsDTO{
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;
    @NotNull(message = "Name is Missing")
    private String name;
    @NotNull(message = "Phone Number is Missing")
    private String phoneNumber;
    @NotNull(message = "Relationship is Missing")
    private String relationship;
    @NotNull(message = "Residence is Missing")
    private String residence;
    @NotNull(message = "Email is Missing")
    private String email;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long employee_id;
}
