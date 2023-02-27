package com.metro.setups.biodatasetups.children.repository;

import com.metro.setups.biodatasetups.children.specifications.Children;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildrenRepository extends JpaRepository<Children, Long> {
    List<Children> findChildrenByFirstNameIgnoreCaseOrLastNameIgnoreCaseOrSecondNameIgnoreCase(String fname, String sname, String lname);
    boolean existsByFirstNameIgnoreCaseOrSecondNameIgnoreCaseOrLastNameIgnoreCase(String fname, String sname, String lname);
}
