package com.metro.auth.services;

import com.metro.auth.dtos.PasswordResetBody;
import com.metro.auth.dtos.UserData;
import com.metro.auth.dtos.AuthenticationRequest;
import com.metro.auth.dtos.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<?> registerUser(UserData data);
    boolean containsWhitespace(String str);

    public JwtResponse authenticate(AuthenticationRequest request);

    public ResponseEntity<?> resetPassword(PasswordResetBody passwordResetBody);

    ResponseEntity<?> updatePassword(PasswordResetBody passwordResetBody);
}
