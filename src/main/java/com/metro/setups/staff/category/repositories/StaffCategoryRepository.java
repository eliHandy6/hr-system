package com.metro.setups.staff.category.repositories;

import com.metro.setups.staff.category.specifications.StaffCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface StaffCategoryRepository extends JpaRepository<StaffCategory, Long> {

    boolean existsStaffCategoryByCategoryNameIgnoreCase(String name);

    List<StaffCategory> findByCategoryNameContainingIgnoreCase(String name);


}
