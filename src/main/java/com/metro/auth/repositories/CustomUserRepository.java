package com.metro.auth.repositories;

import java.util.Optional;

import com.metro.auth.specification.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomUserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
