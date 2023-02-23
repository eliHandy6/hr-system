package com.metro.setups.staff.businessunit.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.staff.businessunit.dtos.BusinessUnitDto;
import com.metro.setups.staff.businessunit.specifications.BusinessUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface BusinessUnitService {

    public ApiResponse createBusinessUnit(BusinessUnitDto businessUnitDto);


    public ApiResponse updateBusinessUnit(Long id, BusinessUnitDto businessUnitDto);


    public ApiResponse getBusinessUnits();


    public ApiResponse deleteBusinessUnits(Long id);

    public ApiResponse selectBusinessUnitByName(String name);

    public ApiResponse selectBusinessUnitById(Long id);

    public boolean existsBusinessUnitByName(String name);
}
