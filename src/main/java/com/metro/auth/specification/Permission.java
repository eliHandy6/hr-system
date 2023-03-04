package com.metro.auth.specification;

import com.metro.audit.Auditable;
import com.metro.auth.dtos.PermissionData;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "auth_permission", schema="hrm")
public class Permission extends Auditable {

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
