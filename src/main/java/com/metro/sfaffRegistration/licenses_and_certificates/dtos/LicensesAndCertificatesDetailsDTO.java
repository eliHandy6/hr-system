package com.metro.sfaffRegistration.licenses_and_certificates.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
public class LicensesAndCertificatesDetailsDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;
    @NotNull(message = "License condition is missing")
    private String condition;
    @NotNull(message = "License type is missing")
    private String licenseType;
    @NotNull(message = "License number is missing")
    private String licenseNumber;
    @NotNull(message = "License issuer name is missing")
    private String issuerName;
    @NotNull(message = "License details is missing")
    private String licenseDetails;
    @NotNull(message = "License expiry date is missing")
    private LocalDate expiryDate;
    @NotNull(message = "License date of issue is missing")
    private  LocalDate dateOfIssue;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long employee_id;

}
