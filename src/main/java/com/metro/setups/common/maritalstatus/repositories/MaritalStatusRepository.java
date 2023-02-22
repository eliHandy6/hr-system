package com.metro.setups.common.maritalstatus.repositories;

import com.metro.setups.common.maritalstatus.specifications.MaritalStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface MaritalStatusRepository extends JpaRepository<MaritalStatus, Long> {

    boolean existsMaritalStatusByMaritalStatusNameIgnoreCase(String name);

    List<MaritalStatus> findByMaritalStatusNameContainingIgnoreCase(String name);
}
