package com.metro.setups.biodatasetups.spouse.services;

import com.metro.core.ApiResponse;
import com.metro.setups.biodatasetups.spouse.dtos.SpouseDetailsDTO;

public interface SpouseDetailsService {
    ApiResponse createSpouseDetails(SpouseDetailsDTO spouseDetailsDTO);

    ApiResponse updateSpouseDetails(Long id, SpouseDetailsDTO spouseDetailsDTO);

    ApiResponse getSpouseDetails();

    ApiResponse selectSpouseDetailsByName(String name);

    ApiResponse selectSpouseDetailsByID(Long id);

    boolean spouseDetailsExists(String name);
}
