package com.vikas.smartscholar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResearchPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 10000)
    private String abstractText;

    private String authors;

    @ElementCollection
    private List<String> tags;

    @Column(length = 3000)
    private String summary;

    private String pdfUrl;

    private LocalDate publishedAt;

    @ManyToOne
    private User uploadedBy;
}
