package com.metro.setups.designation.service;

import com.metro.core.ApiResponse;
import com.metro.setups.designation.dtos.DesignationDTO;
/**
 * @Author: Lentumunai Mark
 * Version :1.0.0
 * Email:marklentumunai@gmail.com
 **/

public interface DesignationService {
    ApiResponse createDesignation(DesignationDTO designationDTO);

    ApiResponse updateDesignation(Long id, DesignationDTO designationDTO);

    ApiResponse getDesignations();

    ApiResponse selectDesignationByName(String name);

    ApiResponse selectDesignationByID(Long id);
    boolean designationExists(String name);
}
