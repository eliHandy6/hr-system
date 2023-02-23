package com.metro.setups.staff.leavecategory.services;

import com.metro.core.ApiResponse;
import com.metro.setups.staff.leavecategory.dtos.LeaveCategoryDto;


/**
 * @Author: Zack Ogoma
 * Version :1.0.0
 * Email:zackogoma@gmail.com
 **/
public interface LeaveCategoryService {

    ApiResponse createLeaveCategory(LeaveCategoryDto leaveCategoryDto);

    ApiResponse updateLeaveCategory(Long id, LeaveCategoryDto leaveCategoryDto);

    ApiResponse getLeaveCategories();

    ApiResponse deleteLeaveCategory(Long id);

    ApiResponse selectLeaveCategoryByName(String name);

    ApiResponse selectLeaveCategoryByID(Long id);

    boolean existsLeaveCategoryByName(String name);
}
