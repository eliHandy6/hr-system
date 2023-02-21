package com.metro.auth.dtos;

import lombok.Data;

import java.util.List;

@Data

public class JwtResponse {
    private String jwtToken;
    private Integer id;
    private String username;
    private String email;
    List<String> role;

    public JwtResponse(String jwtToken, Integer id, String username, String email, List<String> role) {
        this.jwtToken = jwtToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
