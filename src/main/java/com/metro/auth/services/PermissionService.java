package com.metro.auth.services;

import com.metro.auth.specification.Permission;

import java.util.List;

public interface PermissionService {
    public List<Permission> findAll();
}
