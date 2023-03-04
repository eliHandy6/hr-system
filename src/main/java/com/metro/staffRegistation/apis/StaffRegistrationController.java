package com.metro.staffRegistation.apis;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.setups.staff.payrollgroup.dtos.PayrollGroupDto;
import com.metro.staffRegistation.dto.StaffRegistationDetailsDto;
import com.metro.staffRegistation.service.StaffRegistrationservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/


@RestController
@RequestMapping("api/v1/staffs")
@Tag(name = "Staff Registration")
@RequiredArgsConstructor
@Slf4j
public class StaffRegistrationController {

    private final StaffRegistrationservice staffRegistrationservice;


    @Operation(summary = "create staff")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = " Successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflict ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<?> createStaff(@RequestBody @Valid StaffRegistationDetailsDto staffRegistationDetailsDto) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to save the staff")
                .success(false)
                .data(staffRegistationDetailsDto)
                .build();
        try {
            response = staffRegistrationservice.createStaff(staffRegistationDetailsDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateResourceException duplicateResourceException) {
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
