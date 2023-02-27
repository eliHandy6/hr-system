package com.metro.setups.biodatasetups.spouse.repositories;

import com.metro.setups.biodatasetups.spouse.specification.SpouseDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpouseDetailsRepository extends JpaRepository<SpouseDetails, Long> {
    List<SpouseDetails> findSpouseDetailsByNameIgnoreCase(String name);
    boolean existsSpouseDetailsByNameIgnoreCase(String name);
}
