package com.metro.auth.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class RoleData {
    private Long id;
    private String name;

    @NotNull(message = "Description is missing")
    private String description;
    private boolean disabled;

    public RoleData(Long id, String name, String description, boolean disabled) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.disabled = disabled;
    }

    public RolePermissionData toRolePermissionData(final Collection<PermissionData> permissionData){
        return new RolePermissionData(this.id, this.name, this.description, this.disabled, permissionData);
    }

}
