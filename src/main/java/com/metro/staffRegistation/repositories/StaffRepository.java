package com.metro.staffRegistation.repositories;

import com.metro.staffRegistation.specifications.StaffRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface StaffRepository extends JpaRepository<StaffRegistration, Long> {
}
