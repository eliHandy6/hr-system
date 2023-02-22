package com.metro.setups.districts.repositories;

import com.metro.setups.districts.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/
public interface DistrictRepository extends JpaRepository<District, Long> {
    List<District> findDistrictByNameIgnoreCase(String name);
    boolean existsDistrictByNameIgnoreCase(String name);
}
