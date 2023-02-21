package com.metro.auth.apis;

import com.metro.auth.services.PermissionService;
import com.metro.auth.specification.Permission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permission) {
        this.permissionService = permission;
    }

    @GetMapping
    public ResponseEntity<?> retrieveAllRoles()
    {
        List<Permission> permissions = permissionService.findAll();
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

}
