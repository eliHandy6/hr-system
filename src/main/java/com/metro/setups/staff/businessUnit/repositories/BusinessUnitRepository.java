package com.metro.setups.staff.businessUnit.repositories;

import com.metro.setups.staff.businessUnit.specifications.BusinessUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface BusinessUnitRepository extends JpaRepository<BusinessUnit, Long> {

    boolean existsBusinessUnitByBusinessUnitNameContainingIgnoreCase(String name);

    List<BusinessUnit> findByBusinessUnitNameContainingIgnoreCase(String name);
}
