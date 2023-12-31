package com.metro.setups.staff.jobcategories.dtos;

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
public class JobCategoryDTO {
    @NotNull(message = "Name of Job category cannot be empty")
    private  String name;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long jobCategoryId;

    private Long jobSubcategoryId;
}
