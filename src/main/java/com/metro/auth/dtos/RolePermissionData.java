package com.metro.auth.dtos;

import java.util.Collection;

public record RolePermissionData(Long id, String name, String description,
                                 boolean disabled, Collection<PermissionData> permissionData) {


}
