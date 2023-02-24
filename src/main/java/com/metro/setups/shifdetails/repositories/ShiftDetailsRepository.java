package com.metro.setups.shifdetails.repositories;

import com.metro.setups.shifdetails.entities.ShiftDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShiftDetailsRepository extends JpaRepository<ShiftDetails, Long> {
    List<ShiftDetails> findShiftDetailsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

}
