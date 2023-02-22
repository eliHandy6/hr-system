package com.metro.setups.titles.services.impl;

import com.metro.core.ApiResponse;
import com.metro.exceptions.APIExceptions;
import com.metro.exceptions.ApiResponses;
import com.metro.exceptions.DuplicateResourceException;
import com.metro.exceptions.ResourceNotFoundException;
import com.metro.setups.titles.Entity.Titles;
import com.metro.setups.titles.dtos.TitleDto;
import com.metro.setups.titles.repositories.TitleRepository;
import com.metro.setups.titles.services.TitleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TitleServiceImpl implements TitleService {
    private final TitleRepository titleRepository;

    public TitleServiceImpl(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }

    @Override
    public ApiResponses getAll() {
        ApiResponses apiResponse = ApiResponses.builder()
                .message("Failed to create title")
                .success(false)
                .data(null)
                .build();
        List<Titles> list = titleRepository.findAll();
        apiResponse.setMessage("Created Title Successfully");
        apiResponse.setSuccess(true);
        apiResponse.setData(list);
        return apiResponse;
    }

    @Override
    public ApiResponses createTitle(TitleDto titleDto) {
        ApiResponses apiResponse = ApiResponses.builder()
                .message("Failed to create title")
                .success(false)
                .data(titleDto)
                .build();
        String name = titleDto.getName();
        if((name.trim()).length() == 0) throw APIExceptions.badRequest("Name cannot be empty");
        if (titleRepository.findTitlesByName(name).isPresent()) {
            throw new DuplicateResourceException("Title " + name +" already exists in the database try another");
        }

        Titles titles = new Titles(titleDto.getName());
        titles = titleRepository.save(titles);
        titleDto.setId((Long) titles.getId());
        apiResponse.setData(titleDto);
        apiResponse.setMessage("Created Title Successfully");
        apiResponse.setSuccess(true);
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
        ApiResponses apiResponse = ApiResponses.builder()
                .message("Failed to get title by name")
                .success(false)
                .data(null)
                .build();
        Titles titles = titleRepository.findTitlesByName(name).orElseThrow(() -> new ResourceNotFoundException(" Title with name : " + name + "not found"));
        apiResponse.setData(titles);
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
                .data(null)
                .build();

        Titles titles = titleRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User with id" + id + " Not found")
        );
        apiResponse.setMessage("Successfully fetched user with given Id");
        apiResponse.setSuccess(true);
        apiResponse.setData(titles);
        return apiResponse;

    }
}
