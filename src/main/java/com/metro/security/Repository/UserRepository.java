package com.metro.security.Repository;

import com.metro.security.specification.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
