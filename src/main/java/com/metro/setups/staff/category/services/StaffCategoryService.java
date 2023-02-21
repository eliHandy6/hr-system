package com.metro.setups.staff.category.services;

import com.metro.core.ApiResponse;
import com.metro.setups.staff.category.dtos.StaffCategoryDto;


/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface StaffCategoryService {

    ApiResponse createStaffCategory(StaffCategoryDto staffCategoryDto);

    ApiResponse updateStaffCategory(Long id, StaffCategoryDto staffCategoryDto);

    ApiResponse getAllStaffCategory();

    ApiResponse deleteStaffCategoryById(Long id);

    ApiResponse selectStaffCategoryByName(String name);

    ApiResponse selectStaffCategoryByID(Long id);

    boolean existsStaffCategoryName(String name);


}
