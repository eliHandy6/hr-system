package com.metro.auth.services.impl;

import com.metro.auth.repositories.UserRepository;
import com.metro.auth.services.UserService;
import com.metro.auth.specification.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    public Page<User> searchAllUsers(String search, Pageable pageable) {
        Specification<User> getSpec =createSpecification(search);
        return userRepository.findAll(getSpec, pageable);
    }

    public static Specification<User> createSpecification(String search) {

        return (root, query, cb) -> {

            final ArrayList<Predicate> predicates = new ArrayList<>();

            if (search != null) {
                final String likeExpression = "%" + search + "%";
                predicates.add( cb.like(root.get("name"), likeExpression));
            }
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
    public Optional<User> finderUsernameOrEmail(String username) {
        return userRepository.findByUsernameOrEmail(username, username);
    }

    public Optional<User> findUserByEmail(String userEmail) {
            return userRepository.findByEmail(userEmail);
    }
}
