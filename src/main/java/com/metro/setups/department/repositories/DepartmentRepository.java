package com.metro.setups.department.repositories;

import com.metro.setups.department.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
**/
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
