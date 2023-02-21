package com.metro.auth.services.impl;

import com.metro.auth.dtos.PasswordResetBody;
import com.metro.auth.dtos.UserData;
import com.metro.auth.services.AuthenticationService;
import com.metro.auth.services.UserService;
import com.metro.exceptions.APIExceptions;
import com.metro.exceptions.ApiResponse;
import com.metro.auth.repositories.RoleRepository;
import com.metro.auth.repositories.UserRepository;
import com.metro.auth.dtos.AuthenticationRequest;
import com.metro.auth.dtos.JwtResponse;
import com.metro.config.security.JwtService;
import com.metro.auth.specification.Role;
import com.metro.auth.specification.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository repository;
    private final UserService userService;


    public ResponseEntity<?> registerUser(UserData data) {
        System.out.println("No test case passed");
        if (containsWhitespace(data.getUsername()))
            throw APIExceptions.badRequest("Invalid username i.e should not contain whiteSpaces");
        System.out.println("passed first test case");
        if (userRepository.existsByUsername(data.getUsername()))
            return new ResponseEntity(new ApiResponse(false, "Username already taken"), HttpStatus.BAD_REQUEST);
        System.out.println("passed second test case");
        if (userRepository.existsByEmail(data.getEmail()))
            return new ResponseEntity(new ApiResponse(false, "Email is already taken"), HttpStatus.BAD_REQUEST);
        System.out.println("passed the third test case");
        //String password = PasswordGenerator.generate(12);

        User user = new User(data.getEmail().toLowerCase(), data.getUsername().toLowerCase(), data.getPassword(), data.getName());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setPhoneNumber(data.getPhone_number());
        System.out.println(data.getRoles().isEmpty());
        if (!data.getRoles().isEmpty()) {
            Set<Role> userRoles = new HashSet<>();
            for (String role : data.getRoles()) {
                System.out.println("my role is " + role);

                Role userRole = roleRepository.findByName(role).orElseThrow(() -> APIExceptions.internalError("User Role not found."));
                userRoles.add(userRole);
            }
            user.setRoles(userRoles);
        }
        User response = userRepository.save(user);
        //send email to user with credentials
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/v1/auth/users/{username}").buildAndExpand(response.getUsername()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "created user succesfully"));
    }

    public boolean containsWhitespace(String str) {
        String pattern = "\\s";
        return str.matches(pattern);
    }

    public JwtResponse authenticate(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        User userDetails = (User) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        log.info("user with email {} logged in", request.getEmail());
        return new JwtResponse(jwtToken, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    public ResponseEntity<?> resetPassword(PasswordResetBody passwordResetBody) {
        final User user = (User) userService.findUserByEmail(passwordResetBody.getEmail()).orElseThrow(() -> APIExceptions.notFound("No user with email {0} Found", passwordResetBody.getPassword()));
        //generate random code then cache it and compare with user input
        user.setPassword(passwordEncoder.encode(passwordResetBody.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "Password Successfully changed"));

    }
    public ResponseEntity<?> updatePassword(PasswordResetBody passwordResetBody){
        final User user = (User) userService.findUserByEmail(passwordResetBody.getEmail()).orElseThrow(() -> APIExceptions.notFound("No user with email {0} Found", passwordResetBody.getPassword()));
        user.setPassword(passwordEncoder.encode(passwordResetBody.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "Password Successfully changed"));

    }
}
