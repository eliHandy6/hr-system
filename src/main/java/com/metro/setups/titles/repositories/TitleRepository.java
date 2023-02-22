package com.metro.setups.titles.repositories;

import com.metro.setups.titles.Entity.Titles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TitleRepository extends JpaRepository<Titles, Long> {
    Optional<Titles> findTitlesByName(String name);

}
