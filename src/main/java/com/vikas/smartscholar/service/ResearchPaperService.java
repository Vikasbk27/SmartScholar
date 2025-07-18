package com.vikas.smartscholar.service;

import com.vikas.smartscholar.dto.AdvancedSearchRequest;
import com.vikas.smartscholar.dto.ResearchPaperRequest;
import com.vikas.smartscholar.dto.ResearchPaperResponse;
import com.vikas.smartscholar.exception.AccessDeniedException;
import com.vikas.smartscholar.model.ResearchPaper;
import com.vikas.smartscholar.model.Role;
import com.vikas.smartscholar.model.User;
import com.vikas.smartscholar.repository.ResearchPaperRepository;
import com.vikas.smartscholar.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResearchPaperService {

    private final ResearchPaperRepository researchPaperRepository;
    private final UserRepository userRepository;


    public ResearchPaperService(ResearchPaperRepository researchPaperRepository, UserRepository userRepository) {
        this.researchPaperRepository = researchPaperRepository;
        this.userRepository = userRepository;
    }

public ResearchPaperResponse uploadPaper(ResearchPaperRequest request) {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
    // Check if the current user is an admin
    if (currentUser.getRole() != Role.ADMIN) {
        throw new AccessDeniedException("Only administrators can upload papers");
    }
    
    ResearchPaper paper = new ResearchPaper();
    paper.setTitle(request.getTitle());
    paper.setAbstractText(request.getAbstractText());
    paper.setAuthors(request.getAuthors());
    paper.setTags(request.getTags());
    paper.setSummary(request.getSummary());
    paper.setPdfUrl(request.getPdfUrl());
    paper.setPublishedAt(LocalDate.now());
    paper.setUploadedBy(currentUser);

    ResearchPaper savedPaper = researchPaperRepository.save(paper);
    return convertToResponse(savedPaper);
}

    public List<ResearchPaperResponse> getAllPapers() {
        return researchPaperRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ResearchPaperResponse getPaperById(Long id) {
        ResearchPaper paper = researchPaperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paper not found"));
        return convertToResponse(paper);
    }

    public List<ResearchPaperResponse> searchPapers(String query) {
        return researchPaperRepository.searchPapers(query).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ResearchPaperResponse> getPapersByTag(String tag) {
        return researchPaperRepository.findByTagsContainingIgnoreCase(tag).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private ResearchPaperResponse convertToResponse(ResearchPaper paper) {
        ResearchPaperResponse response = new ResearchPaperResponse();
        response.setId(paper.getId());
        response.setTitle(paper.getTitle());
        response.setAbstractText(paper.getAbstractText());
        response.setAuthors(paper.getAuthors());
        response.setTags(paper.getTags());
        response.setSummary(paper.getSummary());
        response.setPdfUrl(paper.getPdfUrl());
        response.setPublishedAt(paper.getPublishedAt());
        response.setUploadedByUsername(paper.getUploadedBy().getUsername());
        return response;
    }

    public void deletePaper(Long id) {
        researchPaperRepository.deleteById(id);
    }

    public ResearchPaper updatePaper(Long id, ResearchPaperRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // Check if the current user is an admin
        if (currentUser.getRole() != Role.ADMIN) {
            throw new AccessDeniedException("Only administrators can update papers");
        }
        
        ResearchPaper paper = researchPaperRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paper not found"));
        
        paper.setTitle(request.getTitle());
        paper.setAbstractText(request.getAbstractText());
        paper.setAuthors(request.getAuthors());
        paper.setTags(request.getTags());
        paper.setSummary(request.getSummary());
        paper.setPdfUrl(request.getPdfUrl());
        
        return researchPaperRepository.save(paper);
    }

        public List<ResearchPaperResponse> advancedSearch(AdvancedSearchRequest request) {
        return researchPaperRepository.advancedSearch(
                request.getTitle(),
                request.getAuthor(),
                request.getTag(),
                request.getFromDate(),
                request.getToDate()
        ).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ResearchPaperResponse> findByMultipleTags(List<String> tags) {
        return researchPaperRepository.findByMultipleTags(
                tags.stream().map(String::toLowerCase).collect(Collectors.toList()),
                tags.size()
        ).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ResearchPaperResponse> findSimilarPapers(Long paperId) {
        return researchPaperRepository.findSimilarPapers(paperId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
}