package com.metro.security.DTO;

import java.util.Collection;

public record RolePermissionData(Long id, String name, String description,
                                 boolean disabled, Collection<PermissionData> permissionData) {


}
