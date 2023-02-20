package com.metro.security.service;

import com.metro.security.DTO.PermissionData;
import com.metro.security.DTO.RoleData;
import com.metro.security.DTO.RolePermissionData;
import com.metro.security.specification.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    public RoleData createRole(RoleData roleData);


    Optional<Role> roleNameExists(String name);

    public String changeStatusOfTheRole(Long id, String name);

    Role getRoleToChange(Long id);

    public Role getRoleById(Long id);

    public RoleData updateRole(Long roleId, RoleData roleData);

    public RolePermissionData getRolePermissions(Long roleId);

    public RoleData updateRolePermissions(Long roleId, List<PermissionData> data);

    public void deleteRole(Long code);

    public List<Role> findAll();
}
