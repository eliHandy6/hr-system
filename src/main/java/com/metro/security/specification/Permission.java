package com.metro.security.specification;

import com.metro.security.DTO.PermissionData;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "auth_permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String permissionGroup;

    public boolean isMatch(String permission) {
        return this.name.equalsIgnoreCase(permission);
    }
    public PermissionData toData() {
        PermissionData data = new PermissionData();
        data.setId(this.getId());
        data.setName(this.name);
        data.setPermissionGroup(this.permissionGroup);
        return data;
    }

}
