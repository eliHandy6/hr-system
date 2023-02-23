package com.metro.setups.common.nationality.repositories;

import com.metro.setups.common.nationality.specifications.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface NationalityRepository extends JpaRepository<Nationality, Long> {

    boolean existsNationalityByNationalityNameContainingIgnoreCase(String name);

    List<Nationality> findByNationalityNameContainingIgnoreCase(String name);
}
