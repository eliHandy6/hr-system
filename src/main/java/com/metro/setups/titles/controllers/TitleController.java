package com.metro.setups.titles.controllers;

import com.metro.exceptions.APIExceptions;
import com.metro.exceptions.ApiResponses;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.titles.dtos.TitleDto;
import com.metro.setups.titles.services.TitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/title")
@Tag(name = "titles")
public class TitleController {
    private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @PostMapping
    @Operation(description = "Create a title")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = " Successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Conflict ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> createTitle(

            @RequestBody @Valid TitleDto titleDto
    ) {
        ApiResponses response = ApiResponses.builder()
                .message("Failed to create title")
                .success(false)
                .data(titleDto)
                .build();
        try {
            response = titleService.createTitle(titleDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (APIExceptions apiExceptions) {
            response.setMessage(apiExceptions.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(description = "update the title")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Title not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> updateTitle(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid TitleDto titleDto
    ) {
        ApiResponses response = ApiResponses.builder()
                .message("Failed to update title")
                .success(false)
                .data(titleDto)
                .build();
        try {
            response = titleService.updateTitle(id, titleDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    @Operation(description = "Get all the titles")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully retrieved all titles",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Client Error",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Title not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> getAllTitles() {
        ApiResponses response = ApiResponses.builder()
                .message("Failed to get all the titles")
                .success(false)
                .data(null)
                .build();
        try {
            response = titleService.getAll();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    @Operation(description = "Get the title by name")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully searched for the title",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Title not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> fetchTitleByName(
            @RequestParam String name
    ) {
        ApiResponses response = ApiResponses.builder()
                .message("Failed to get the title")
                .success(false)
                .data(null)
                .build();
        try {
            response = titleService.getByName(name);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            response.setMessage(exception.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(description = "Get the title by Id")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully Retrieved the title by Id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Title not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> getTitleById(@PathVariable Long id) {
        ApiResponses response = ApiResponses.builder()
                .message("Failed to get the title by Id")
                .success(false)
                .data(null)
                .build();
        try {
            response = titleService.getById(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException resourceNotFoundException) {
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete the title by Id")
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = " Successfully deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = com.metro.core.ApiResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid body",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Title not found ",
                    content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server error",
                    content = @Content)})
    public ResponseEntity<?> deleteT(
            @PathVariable Long id) {
        ApiResponses response = ApiResponses.builder()
                .message("Failed to delete the title by Id")
                .success(false)
                .data(null)
                .build();
        try{
            response = titleService.deleteTitle(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (ResourceNotFoundException resourceNotFoundException){
            response.setMessage(resourceNotFoundException.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}



