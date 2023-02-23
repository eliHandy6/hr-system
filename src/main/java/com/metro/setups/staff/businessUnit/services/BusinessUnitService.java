package com.metro.setups.staff.businessUnit.services;

import com.metro.core.ApiResponse;
import com.metro.setups.staff.businessUnit.dtos.BusinessUnitDto;


/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface BusinessUnitService {

    ApiResponse createBusinessUnit(BusinessUnitDto businessUnitDto);

    ApiResponse updateBusinessUnit(Long id, BusinessUnitDto businessUnitDto);

    ApiResponse getBusinessUnits();

    ApiResponse deleteBusinessUnits(Long id);

    ApiResponse selectBusinessUnitByName(String name);

    ApiResponse selectBusinessUnitById(Long id);

    boolean existsBusinessUnitByName(String name);
}
