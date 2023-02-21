package com.metro.auth.dtos;

import java.util.List;

public class UserPermission {
    private  String group;
    private List<PermissionData> permissions;

    public UserPermission(String group, List<PermissionData> permissions) {
        this.group = group;
        this.permissions=permissions;
    }
}
