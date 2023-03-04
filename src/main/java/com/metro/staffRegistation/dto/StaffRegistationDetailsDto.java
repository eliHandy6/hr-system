package com.metro.staffRegistation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Builder
public class StaffRegistationDetailsDto {

    @NotNull(message = "Staff category is missing")
    @Positive(message = "positive values only allowed")
    private Long staffCategoryId;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long employeeId;


    @NotEmpty
    @NotNull(message = "Staff first name is missing")
    private String firstName;

    @NotEmpty
    @NotNull(message = "Staff second name is missing")
    private String secondName;


    private String otherNames;

    @NotNull(message = "Staff title  is missing")
    @Positive(message = "positive values only allowed")
    private Long titleId;

    @NotNull(message = "job category id  is missing")
    @Positive(message = "positive values only allowed")
    private Long jobCategoryId;


    @NotNull(message = "job sub category   is missing")
    @Positive(message = "positive values only allowed")
    private Long jobSubCategoryId;


    @NotNull(message = "business unit id   is missing")
    @Positive(message = "positive values only allowed")
    private Long businessUnitId;


    @NotNull(message = "notice period id   is missing")
    @Positive(message = "positive values only allowed")
    private Long noticePeriodId;


    @NotNull(message = "payroll group id  is missing")
    @Positive(message = "positive values only allowed")
    private Long payrollGroupId;


    @NotNull(message = "Staff Department id is missing")
    @Positive(message = "positive values only allowed")
    private Long departmentId;


    @NotNull(message = "Staff section  id is missing")
    @Positive(message = "positive values only allowed")
    private Long sectionId;

    @NotNull(message = "Staff designation  id is missing")
    @Positive(message = "positive values only allowed")
    private Long designationId;


    @NotNull(message = "Staff job group id is missing")
    @Positive(message = "positive values only allowed")
    private Long staffJobGroupId;


    @NotNull(message = "Staff leave category  id is missing")
    @Positive(message = "positive values only allowed")
    private Long staffLeaveCategoryId;

    @NotNull(message = "Interview date is missing  ")
    private LocalDate interviewDate;


    @NotNull(message = "report date is missing  ")
    private LocalDate reportDate;


    @NotNull(message = "Staff field of specialization is missing")
    @NotEmpty
    private String specialization;


    @NotNull(message = "Staff position applied for is missing")
    @NotEmpty
    private String positionAppliedFor;
}
