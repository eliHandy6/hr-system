package com.metro.setups.common.maritalstatus.services;

import com.metro.core.ApiResponse;
import com.metro.setups.common.maritalstatus.dtos.MaritalStatusDto;


/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface MaritalStatusService {

    ApiResponse createMaritalStatus(MaritalStatusDto staffCategoryDto);

    ApiResponse updateMaritalStatus(Long id, MaritalStatusDto staffCategoryDto);

    ApiResponse getMaritalStatuses();

    ApiResponse deleteMaritalStatus(Long id);

    ApiResponse selectMaritalStatusByName(String name);

    ApiResponse selectMaritalStatusByID(Long id);

    boolean existsMaritalStatusName(String name);
}
