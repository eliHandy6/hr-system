package com.metro.setups.staff.payrollgroup.repositories;

import com.metro.setups.staff.payrollgroup.specifications.PayrollGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface PayrollGroupRepository extends JpaRepository<PayrollGroup, Long> {

    boolean existsPayrollGroupByPayrollGroupNameContainingIgnoreCase(String name);

    List<PayrollGroup> findByPayrollGroupNameContainingIgnoreCase(String name);
}
