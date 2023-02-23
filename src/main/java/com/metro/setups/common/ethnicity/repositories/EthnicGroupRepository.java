package com.metro.setups.common.ethnicity.repositories;

import com.metro.setups.common.ethnicity.specifications.EthnicGroup;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface EthnicGroupRepository extends JpaRepository<EthnicGroup, Long> {

    boolean existsEthnicGroupByEthnicGroupNameContainingIgnoreCase(String name);

    List<EthnicGroup> findByEthnicGroupNameContainingIgnoreCase(String name);
}
