package com.metro.auth.apis;

import com.metro.auth.dtos.AuthenticationRequest;
import com.metro.auth.dtos.JwtResponse;
import com.metro.auth.dtos.PasswordResetBody;
import com.metro.auth.dtos.UserData;
import com.metro.auth.services.AuthenticationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping

    @PreAuthorize("hasAnyRole('ADMIN', 'HR')")
    @Operation(
            description = "Register user Service",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"),
                    @ApiResponse(
                            responseCode = "200",
                            description = "User Successfully created!",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"User Successfully registered!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody UserData data
    ) {
        return authenticationService.registerUser(data);
    }

    @PostMapping("/authenticate")
    @Operation(
            description = "Sign in Service",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully Signed In!",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Successfully signed In!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<?> authenticate(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\"username\" : \"admin test\", \"password\" : \"**********\"}"
                                    ),
                            }
                    ))
            @RequestBody AuthenticationRequest request
    ) {
        JwtResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    //reset password
    @ResponseBody
    @PostMapping("/user/resetPassword")
    @Operation(
            description = "Rest User password Service",
            responses = {
                    @ApiResponse(responseCode = "400", ref = "badRequestAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI"),
                    @ApiResponse(
                            responseCode = "200",
                            description = "Password Successfully changed!",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{\"code\" : 200, \"Status\" : \"Ok!\", \"Message\" :\"Password Successfully changed!\"}"
                                            ),
                                    }
                            )
                    )
            }
    )
    public ResponseEntity<?> resetPassword(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\"username or email \" : \"example@gmail.com\", \"password\" : \"**********\"}"
                                    ),
                            }
                    ))
            @RequestBody PasswordResetBody passwordResetBody
    ) {
        return authenticationService.resetPassword(passwordResetBody);
    }

    @PutMapping
    public ResponseEntity<?> updatePassword(
            @RequestBody PasswordResetBody passwordResetBody
    ) {
        return authenticationService.updatePassword(passwordResetBody);
    }


}
