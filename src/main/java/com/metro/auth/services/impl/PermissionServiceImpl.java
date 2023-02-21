package com.metro.auth.services.impl;

import com.metro.auth.repositories.PermissionRespository;
import com.metro.auth.services.PermissionService;
import com.metro.auth.specification.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRespository permissionRespository;

    public PermissionServiceImpl(PermissionRespository permissionRespository) {
        this.permissionRespository = permissionRespository;
    }

    public List<Permission> findAll() {
        return  permissionRespository.findAll();
    }
}
