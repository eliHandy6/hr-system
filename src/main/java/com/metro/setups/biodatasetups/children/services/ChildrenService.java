package com.metro.setups.biodatasetups.children.services;

import com.metro.core.ApiResponse;
import com.metro.setups.biodatasetups.children.dtos.ChildrenDTO;

public interface ChildrenService {
    ApiResponse createChildren(ChildrenDTO childrenDTO);

    ApiResponse updateChildren(Long id, ChildrenDTO childrenDTO);

    ApiResponse getChildren();

    ApiResponse selectChildrenByName(String name);

    ApiResponse selectChildrenByID(Long id);
    boolean ChildrenExists(String name);

}
