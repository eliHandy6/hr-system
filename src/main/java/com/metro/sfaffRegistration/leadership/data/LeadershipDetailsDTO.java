package com.metro.sfaffRegistration.leadership.data;

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
public class LeadershipDetailsDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;
    @NotNull(message = "Club Name is missing")
    private String clubName;
    @NotNull(message = "Position Name is missing")
    private String positionName;
    @NotNull(message = "Achievement details are missing")
    private String achievementDetails;
    @NotNull(message = "Start Date is missing")
    private LocalDate beginDate;
    @NotNull(message = "End date is missing")
    private  LocalDate endDate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long employee_id;
}
