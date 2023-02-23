package com.metro.setups.designation.repository;

import com.metro.setups.designation.entities.Designation;
import com.metro.setups.sections.Entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
    List<Designation> findDesignationByNameIgnoreCase(String name);
    boolean existsDesignationByNameIgnoreCase(String name);
}
