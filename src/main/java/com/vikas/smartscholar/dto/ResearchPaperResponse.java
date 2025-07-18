package com.vikas.smartscholar.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ResearchPaperResponse {
    private Long id;
    private String title;
    private String abstractText;
    private String authors;
    private List<String> tags;
    private String summary;
    private String pdfUrl;
    private LocalDate publishedAt;
    private String uploadedByUsername;
}