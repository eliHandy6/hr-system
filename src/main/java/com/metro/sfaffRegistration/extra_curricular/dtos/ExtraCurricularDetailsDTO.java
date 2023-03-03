package com.metro.sfaffRegistration.extra_curricular.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.annotation.Nullable;
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
public class ExtraCurricularDetailsDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long employee_id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long Id;
    @Nullable
    private String socialClubName;
    @Nullable
    private String sportName;
    @Nullable
    private String hobbyName;
    @Nullable
    private String achievements;
}
