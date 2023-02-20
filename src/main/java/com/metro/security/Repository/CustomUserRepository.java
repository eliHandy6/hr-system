package com.metro.security.Repository;

import java.util.Optional;

import com.metro.security.specification.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
