package com.metro.security.service;

import com.metro.security.Repository.PermissionRespository;
import com.metro.security.specification.Permission;
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
