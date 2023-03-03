package com.metro.sfaffRegistration.biodata.children.services;

import com.metro.core.ApiResponse;
import com.metro.sfaffRegistration.biodata.children.dtos.ChildrenDTO;

public interface ChildrenService {
    ApiResponse createChildren(ChildrenDTO childrenDTO, Long id);

    ApiResponse updateChildren(Long id, ChildrenDTO childreDTO, Long child_id);
    ApiResponse selectChildrenByStaffID(Long id);

}
