package com.metro.security.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class UserData {
    private int Id;
    private String password;
    private String email;
    private String username;
    private String phone_number;
    private String name;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean verified;
    private boolean enabled;
    private String langKey = "en";
    private String UUID;
    private List<String> roles = new ArrayList<>();
}
