package com.metro.security.service;

import com.metro.security.DTO.PermissionData;
import com.metro.security.DTO.RoleData;
import com.metro.security.Exceptions.APIExceptions;
import com.metro.security.Repository.PermissionRespository;
import com.metro.security.Repository.RoleRepository;
import com.metro.security.specification.Permission;
import org.springframework.stereotype.Service;
import com.metro.security.specification.Role;
import com.metro.security.DTO.RolePermissionData;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRespository permissionRespository;

    public RoleServiceImp(RoleRepository roleRepository, PermissionRespository permissionRespository) {
        this.roleRepository = roleRepository;
        this.permissionRespository = permissionRespository;
    }

    @Override
    public RoleData createRole(RoleData roleData) {
        //check if user exists
        //then i can create role
        if (roleNameExists(roleData.getName()).isPresent()) {
            //throw exception username exists
        }
        Role role = Role.builder()
                .description(roleData.getDescription())
                .isDisabled(roleData.isDisabled())
                .name(roleData.getName())
                .build();
        Role savedRole = roleRepository.save(role);
        return savedRole.toData();
    }

    @Override
    public Optional<Role> roleNameExists(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public String changeStatusOfTheRole(Long id, String name) {
        Role roleModel = getRoleToChange(id);
        String response = "";
        if (name.equals("enable")) {
            roleModel.setIsDisabled(false);
            response = "Is Disabled set to false";
        } else if (name.equals("disable")) {
            roleModel.setIsDisabled(true);
            response = "Is disabled set to True";
        }
        roleRepository.save(roleModel);
        return response;
    }

    @Override
    public Role getRoleToChange(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> APIExceptions.notFound("Role with given id not found")
        );
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> APIExceptions.notFound("Role with given id not found")
        );
    }

    @Override
    public RoleData updateRole(Long roleId, RoleData roleData) {
        Role role = getRoleById(roleId);
        if (!role.getName().equals(roleData.getName())) {
            role.setName(roleData.getName());
        }
        if (!role.getDescription().equals(roleData.getDescription())) {
            role.setDescription(roleData.getDescription());
        }
        return roleRepository.save(role).toData();
    }

    @Override
    public RolePermissionData getRolePermissions(Long roleId) {
        Role role = getRoleById(roleId);
        Collection<Permission> permissions = role.getPermissions();
        RoleData roleData = role.toData();
        Collection<PermissionData> pd = permissions
                .stream()
                .map(p -> p.toData())
                .collect(Collectors.toSet());

        RolePermissionData permissionsData = roleData.toRolePermissionData(pd);
        return permissionsData;
    }

    @Override
    public RoleData updateRolePermissions(Long roleId, List<PermissionData> data) {
        Role role = getRoleById(roleId);
        List<Permission> list = data.stream()
                .map(permission -> {
                    return permissionRespository.findOneByName(permission.getName()).orElse(null);
                })
                .filter(x -> x != null)
                .collect(Collectors.toList());

        role.updateAllPermissions(list);

        return roleRepository.save(role).toData();
    }

    @Override
    public void deleteRole(Long code) {
        roleRepository.deleteById(code);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
