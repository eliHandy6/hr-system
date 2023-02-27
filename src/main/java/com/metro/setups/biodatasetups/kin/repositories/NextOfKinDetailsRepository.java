package com.metro.setups.biodatasetups.kin.repositories;

import com.metro.setups.biodatasetups.kin.specification.NextOfKinDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NextOfKinDetailsRepository extends JpaRepository<NextOfKinDetails, Long> {
    List<NextOfKinDetails> findNextOfKinDetailsByNameIgnoreCase(String name);
    boolean existsNextOfKinDetailsByNameIgnoreCase(String name);
}
