package com.metro.security.service;

import com.metro.security.specification.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface UserService {
    public Page<User> searchAllUsers(String search, Pageable pageable);

    public static Specification<User> createSpecification(String search) {
        return null;
    }

    public Optional<User> finderUsernameOrEmail(String username);
    public Optional<User> findUserByEmail(String userEmail);
}
