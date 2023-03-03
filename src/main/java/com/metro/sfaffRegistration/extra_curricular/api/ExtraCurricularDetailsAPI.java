package com.metro.sfaffRegistration.extra_curricular.api;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.sfaffRegistration.extra_curricular.dtos.ExtraCurricularDetailsDTO;
import com.metro.sfaffRegistration.extra_curricular.services.ExtraCurricularService;
import com.metro.sfaffRegistration.leadership.data.LeadershipDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/
@RestController
@RequestMapping("/staff/{staff_id}/curricular")
@Tag(name = "EXTRA CURRICULAR DETAILS")
public class ExtraCurricularDetailsAPI {
    private final ExtraCurricularService extraCurricularService;

    public ExtraCurricularDetailsAPI(ExtraCurricularService extraCurricularService) {
        this.extraCurricularService = extraCurricularService;
    }
    @Operation(summary = "Save Extra-Curricular Details.")
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
    public ResponseEntity<?> createExtraCurricularDetails(@PathVariable("staff_id") Long id, @RequestBody @Valid ExtraCurricularDetailsDTO extraCurricularDetailsDTO) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to Save Extra-Curricular Details.")
                .success(false)
                .data(extraCurricularDetailsDTO)
                .build();
        try {
            response = extraCurricularService.createExtraCurricularDetails(extraCurricularDetailsDTO, id);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DuplicateResourceException duplicateResourceException) {
            response.setMessage(duplicateResourceException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "update Extra curricular Details")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully updated ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resource not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @PutMapping("/{leadership_id}")
    public ResponseEntity<?> updateExtraCurricularDetails(@PathVariable("staff_id") Long id, @RequestBody @Valid ExtraCurricularDetailsDTO extraCurricularDetailsDTO, @PathVariable("leadership_id") Long curricular_id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to update Extra Curricular Details")
                .success(false)
                .data(extraCurricularDetailsDTO)
                .build();
        try {
            response = extraCurricularService.updateExtraCurricularDetails(id, extraCurricularDetailsDTO, curricular_id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Operation(summary = "fetch  All Extra Curricular details related to an employee")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully fetched ",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Resource not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Something wrong happened",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<?> getExtraCurricularDetailsById(@PathVariable("staff_id") Long staff_id) {
        ApiResponse response = ApiResponse.builder()
                .message("Failed to get leadership Details. ")
                .success(false)
                .build();
        try {
            response = extraCurricularService.selectExtraCurricularDetailsByID(staff_id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
