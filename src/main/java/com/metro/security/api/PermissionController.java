package com.metro.security.api;

import com.metro.security.service.PermissionService;
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
        List<com.metro.security.specification.Permission> permissions = permissionService.findAll();
        return new ResponseEntity<>(permissions, HttpStatus.OK);
    }

}
