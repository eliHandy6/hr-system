package com.metro.setups.districts.services;

import com.metro.core.ApiResponse;
import com.metro.setups.districts.dtos.DistrictDTO;
import com.metro.setups.sections.dtos.SectionDTO;
import org.springframework.stereotype.Service;

/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/
@Service
public interface DistrictService {
    ApiResponse createDistrict(DistrictDTO districtDTO);

    ApiResponse updateDistrict(Long id,  DistrictDTO districtDTO);

    ApiResponse getDistricts();

    ApiResponse selectDistrictByName(String name);

    ApiResponse selectDistrictByID(Long id);
    boolean districtExists(String name);
}
