package com.vikas.smartscholar.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class AdvancedSearchRequest {
    private String title;
    private String author;
    private String tag;
    private LocalDate fromDate;
    private LocalDate toDate;
    private List<String> tags;
}