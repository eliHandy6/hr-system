package com.metro.utils;

import lombok.Data;

@Data
public class PageDetails {
    private Integer page;
    private Integer perPage;
    private Integer totalPage;
    private Long totalElements;
    private String reportName;
    private String reportPeriod;


}
