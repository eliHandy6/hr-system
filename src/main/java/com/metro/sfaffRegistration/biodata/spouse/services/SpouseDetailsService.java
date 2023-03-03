package com.metro.sfaffRegistration.biodata.spouse.services;

import com.metro.core.ApiResponse;
import com.metro.sfaffRegistration.biodata.spouse.dtos.SpouseDetailsDTO;

public interface SpouseDetailsService {
    ApiResponse createSpouseDetails(SpouseDetailsDTO spouseDetailsDTO, Long id);

    ApiResponse updateSpouseDetails(Long id, SpouseDetailsDTO spouseDetailsDTO, Long spouse_id);


    ApiResponse selectSpouseDetailsByID(Long id);

}
