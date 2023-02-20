package com.metro.security.service;

import com.metro.security.DTO.PasswordResetBody;
import com.metro.security.DTO.UserData;
import com.metro.security.api.AuthenticationRequest;
import com.metro.security.api.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<?> registerUser(UserData data);
    boolean containsWhitespace(String str);

    public JwtResponse authenticate(AuthenticationRequest request);

    public ResponseEntity<?> resetPassword(PasswordResetBody passwordResetBody);

    ResponseEntity<?> updatePassword(PasswordResetBody passwordResetBody);
}
