package com.metro.sfaffRegistration.Addresses.employee_addreses.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.Column;
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
public class EmployeeAddressDetailsDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;
    @NotNull(message = "First primary Mobile Phone number is Missing")
    private String primaryMobileNumber;
    @NotNull(message = "First Secondary Mobile Phone number is Missing")
    private String secondaryMobileNumber;
    @NotNull(message = "First Postal Address is Missing")
    private Long postalAddress;
    @NotNull(message = "First Postal Code is Missing")
    private Long postalCode;
    @NotNull(message = "First Personal Email is Missing")
    private String personalEmail;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long employee_id;
}
