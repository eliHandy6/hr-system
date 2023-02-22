package com.metro.setups.titles.Entity;

import com.metro.audit.Auditable;
import com.metro.setups.titles.dtos.TitleDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "Titles")
@Data
@Builder

public class Titles extends Auditable {
    @NotNull(message = "Title name is missing")
    @Column(name = "TITLE_NAME")
    private String name;

    public Titles() {

    }
    public  Titles(String name){
        this.name = name;
    }

}
