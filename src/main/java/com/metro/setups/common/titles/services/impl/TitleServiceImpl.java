package com.metro.setups.common.titles.services.impl;

import com.metro.exceptions.ApiResponses;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.EmptySpaceExceptionHandler;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.common.titles.Entity.Titles;
import com.metro.setups.common.titles.dtos.TitleDto;
import com.metro.setups.common.titles.repositories.TitleRepository;
import com.metro.setups.common.titles.services.TitleService;
import com.metro.setups.shifdetails.dtos.ShiftDetailsDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TitleServiceImpl implements TitleService {
    private final TitleRepository titleRepository;

    public TitleServiceImpl(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @Override
    public ApiResponses getAll() {
        log.info("Obtaining all the available titles");
        List<TitleDto> titleDtos = new ArrayList<>();
        ApiResponses apiResponse = ApiResponses.builder()
                .message("Failed to create title")
                .success(false)
                .build();
        List<Titles> list = titleRepository.findAll();
        if (!list.isEmpty()) {
            titleDtos.addAll(list.stream().map(e -> {
                TitleDto titleDto = TitleDto.builder()
                        .name(e.getName())
                        .Id(e.getId())
                        .build();
                return titleDto;
            }).toList());
            apiResponse.setMessage("Created Title Successfully");
            apiResponse.setSuccess(true);
            apiResponse.setData(titleDtos);
        }
        return apiResponse;
    }

    @Override
    public ApiResponses createTitle(TitleDto titleDto) {
        log.info("creating title with name {}", titleDto.getName());
        ApiResponses apiResponse = ApiResponses.builder()
                .message("Failed to create title")
                .success(false)
                .data(titleDto)
                .build();
        String name = titleDto.getName();
        if ((name.trim()).length() == 0) throw new EmptySpaceExceptionHandler("Name cannot be empty");
        if (titleRepository.findTitlesByNameIgnoreCase(name).size() != 0) {
            throw new DuplicateResourceException("Title " + name + " already exists in the database try another");
        }
        Titles titles = Titles.builder()
                .name(titleDto.getName())
                .build();
        titles = titleRepository.save(titles);
        if (StringUtils.isNotEmpty(titles.getId().toString())) {
            titleDto.setId(Long.valueOf(titles.getId().longValue()));
            apiResponse.setMessage("Created Title Successfully");
            apiResponse.setData(titleDto);
            apiResponse.setSuccess(true);
        }
        return apiResponse;

    }

    @Override
    public ApiResponses updateTitle(Long id, TitleDto titleDto) {
        ApiResponses apiResponse = ApiResponses.builder()
                .message("Failed to create title")
                .success(false)
                .data(titleDto)
                .build();
        Titles title = titleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Title with id {} not found" + id));
        title.setName(titleDto.getName());
        title = titleRepository.save(title);
        titleDto.setId((Long) title.getId());
        apiResponse.setData(titleDto);
        apiResponse.setMessage("Updated Title Successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponses getByName(String name) {
        log.info("Retrieving all Titles with name {}", name);
        List<TitleDto> titleDtos = new ArrayList<>();
        ApiResponses apiResponse = ApiResponses.builder()
                .message("Failed to get title by name")
                .success(false)
                .data(null)
                .build();
        List<Titles> list = titleRepository.findTitlesByNameIgnoreCase(name);
        if (!list.isEmpty()) {
            titleDtos.addAll(list.stream().map(e -> {
                TitleDto titleDto = TitleDto.builder()
                        .name(e.getName())
                        .Id(e.getId())
                        .build();
                return titleDto;
            }).toList());
        }
        apiResponse.setData(titleDtos);
        apiResponse.setMessage("Completed Search successfully");
        apiResponse.setSuccess(true);
        return apiResponse;
    }

    @Override
    public ApiResponses deleteTitle(Long id) {
        ApiResponses apiResponse = getById(id);
        apiResponse.setMessage("Successfully deleted user with id " + id);
        apiResponse.setSuccess(true);
        titleRepository.deleteById(id);
        return  apiResponse;
    }

    @Override
    public ApiResponses getById(Long id) {
        ApiResponses apiResponse = ApiResponses.builder()
                .message("Failed to get title by Id")
                .success(false)
                .build();

        Titles titles = titleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("title with id" + id + " Not found")
        );
        TitleDto titleDto = TitleDto.builder()
                .name(titles.getName())
                .Id(titles.getId())
                .build();
        apiResponse.setMessage("Successfully fetched title with given Id");
        apiResponse.setSuccess(true);
        apiResponse.setData(titleDto);
        return apiResponse;

    }
}
