package com.metro.auth.dtos;

import lombok.Data;

@Data
public class PermissionData {
    private Long id;
    private String name;
    private String permissionGroup;
}
