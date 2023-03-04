package com.metro.setups.common.titles.Entity;

import com.metro.audit.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "Titles", schema = "hrm")
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
