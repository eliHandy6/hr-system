package com.metro.security.api;

import com.metro.security.DTO.PermissionData;
import com.metro.security.DTO.RoleData;
import com.metro.security.service.RoleService;
import com.metro.security.specification.Role;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.metro.security.DTO.RolePermissionData;

import java.util.List;

@RestController
@RequestMapping("api/v1/roles")
@Tag(name = "Roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity createRole(@Valid @RequestBody RoleData roleData) {
        RoleData response = roleService.createRole(roleData);
        //add status i.e message
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}/enable-disable")
    //preauthorize
    public ResponseEntity changeRoleStatus(
            @PathVariable(value = "id") Long id,
            @RequestParam(value = "command", required = true) Command commandParam
    ) {
        String response = roleService.changeStatusOfTheRole(id, commandParam.name());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public Role getRoleId(
            @PathVariable(value = "id") Long id) {
        return roleService.getRoleById(id);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<?> actionsOnRoles(
            @PathVariable(value = "roleId") Long roleId,
            @Valid @RequestBody RoleData roleData) {
        RoleData data = roleService.updateRole(roleId, roleData);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(data);
    }

    @GetMapping("/{id}/permissions")
    public ResponseEntity<?> getRolePermissions(
            @PathVariable(value = "id") Long roleId) {
        RolePermissionData data = roleService.getRolePermissions(roleId);
        return ResponseEntity.ok(data);
    }

    @PutMapping("/{id}/permissions")
    public ResponseEntity<?> updateRolePermissions(@PathVariable(value = "id") Long roleId, @Valid @RequestBody List<PermissionData> data) {
        RoleData rd = roleService.updateRolePermissions(roleId, data);
        return ResponseEntity.ok(rd);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable(value = "id") Long code) {
        roleService.deleteRole(code);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllRoles() {
        List<Role> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }


}
