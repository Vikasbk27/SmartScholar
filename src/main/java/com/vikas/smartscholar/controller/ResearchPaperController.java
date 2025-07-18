package com.vikas.smartscholar.controller;

import com.vikas.smartscholar.dto.AdvancedSearchRequest;
import com.vikas.smartscholar.dto.ResearchPaperRequest;
import com.vikas.smartscholar.dto.ResearchPaperResponse;
import com.vikas.smartscholar.model.ResearchPaper;
import com.vikas.smartscholar.service.ResearchPaperService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/papers")
public class ResearchPaperController {

    private final ResearchPaperService researchPaperService;


    public ResearchPaperController(ResearchPaperService researchPaperService) {
        this.researchPaperService = researchPaperService;
    }

     @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResearchPaperResponse> uploadPaper(@RequestBody ResearchPaperRequest request) {
        return ResponseEntity.ok(researchPaperService.uploadPaper(request));
    }

    // Public endpoints for viewing papers
    @GetMapping
    public ResponseEntity<List<ResearchPaperResponse>> getAllPapers() {
        return ResponseEntity.ok(researchPaperService.getAllPapers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResearchPaperResponse> getPaperById(@PathVariable Long id) {
        return ResponseEntity.ok(researchPaperService.getPaperById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResearchPaperResponse>> searchPapers(@RequestParam String query) {
        return ResponseEntity.ok(researchPaperService.searchPapers(query));
    }

        @PostMapping("/search/advanced")
    public ResponseEntity<List<ResearchPaperResponse>> advancedSearch(
            @RequestBody AdvancedSearchRequest request) {
        return ResponseEntity.ok(researchPaperService.advancedSearch(request));
    }

    @PostMapping("/search/tags")
    public ResponseEntity<List<ResearchPaperResponse>> searchByTags(
            @RequestBody List<String> tags) {   
        return ResponseEntity.ok(researchPaperService.findByMultipleTags(tags)); 
    }
    //   [ "AI","machine learning"]

    @GetMapping("/{id}/similar")
    public ResponseEntity<List<ResearchPaperResponse>> getSimilarPapers(
            @PathVariable Long id) {
        return ResponseEntity.ok(researchPaperService.findSimilarPapers(id));
    }

    // @GetMapping("/tag/{tag}")
    // public ResponseEntity<List<ResearchPaperResponse>> getPapersByTag(@PathVariable String tag) {
    //     return ResponseEntity.ok(researchPaperService.getPapersByTag(tag));
    // }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResearchPaper> updatePaper(@PathVariable Long id, @RequestBody ResearchPaperRequest request) {
        return ResponseEntity.ok(researchPaperService.updatePaper(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deletePaper(@PathVariable Long id) {
        researchPaperService.deletePaper(id);
        return ResponseEntity.noContent().build();  
    }
}