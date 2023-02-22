package com.metro.setups.staff.leavecategory.repositories;

import com.metro.setups.staff.leavecategory.specifications.LeaveCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface LeaveCategoryRepository extends JpaRepository<LeaveCategory, Long> {

    boolean existsLeaveCategoryByLeaveCategoryNameContainingIgnoreCase(String name);

    List<LeaveCategory> findByLeaveCategoryNameContainingIgnoreCase(String name);
}
