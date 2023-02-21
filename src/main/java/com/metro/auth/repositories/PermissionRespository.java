package com.metro.auth.repositories;

import com.metro.auth.specification.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRespository extends JpaRepository<Permission, Long> {
    Optional<Permission> findOneByName(String code);
}
