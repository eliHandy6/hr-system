package com.metro.setups.common.titles.repositories;

import com.metro.setups.common.titles.Entity.Titles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TitleRepository extends JpaRepository<Titles, Long> {
    Optional<Titles> findTitlesByNameIgnoreCase(String name);

}
