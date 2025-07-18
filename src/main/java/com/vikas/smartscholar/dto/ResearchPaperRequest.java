package com.vikas.smartscholar.dto;

import lombok.Data;
import java.util.List;

@Data
public class ResearchPaperRequest {
    private String title;
    private String abstractText;
    private String authors;
    private List<String> tags;
    private String summary;
    private String pdfUrl;
}


// authorization : Bearer Token
// Token : http://localhost:8080/api/auth/login (Get from doing admin login)

// {
//     "title": "Test Paper3",
//     "abstractText": "This is a test abstract",
//     "authors": "Roman Reings",
//     "tags": [
//         "AI",
//         "Machine Learning"
//     ],
//     "summary": "Test summary",
//     "pdfUrl": "http://example.com/paper.pdf",
//     "publishedAt": "2025-07-15",
//     "uploadedByUsername": "vikasbk2707@gmail.com"
// }