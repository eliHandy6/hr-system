package com.metro.setups.common.titles.services;

import com.metro.exceptions.ApiResponses;
import com.metro.setups.common.titles.dtos.TitleDto;

public interface TitleService {
    ApiResponses createTitle(TitleDto titleDto);
    ApiResponses updateTitle(Long id, TitleDto titleDto);
    ApiResponses getAll();

    ApiResponses getByName(String name);

    ApiResponses deleteTitle(Long id);

    ApiResponses getById(Long id);
}
