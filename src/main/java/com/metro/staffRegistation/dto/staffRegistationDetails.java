package com.metro.staffRegistation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public class staffRegistationDetails {

    @NotNull(message = "Staff category is missing")
    @Positive(message = "positive values only allowed")
    private Long staffCategoryId;


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long employeeId;


    @NotNull(message = "Staff first name is missing")
    private String firstName;

    @NotNull(message = "Staff second name is missing")
    private String secondName;


    private String otherNames;

    @NotNull(message = "Staff title  is missing")
    @Positive(message = "positive values only allowed")
    private Long titleId;

    @NotNull(message = "Staff Department id is missing")
    @Positive(message = "positive values only allowed")
    private Long departmentID;


    @NotNull(message = "Staff section  id is missing")
    @Positive(message = "positive values only allowed")
    private Long sectionID;

    @NotNull(message = "Staff designation  id is missing")
    @Positive(message = "positive values only allowed")
    private Long designationID;

    @NotNull(message = "Interview date is missing  ")
    private LocalDate interviewDate;


    @NotNull(message = "Staff field of specialization is missing")
    private String specialization;
}
