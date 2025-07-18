package com.vikas.smartscholar.repository;

import com.vikas.smartscholar.model.ResearchPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ResearchPaperRepository extends JpaRepository<ResearchPaper, Long> {
    List<ResearchPaper> findByUploadedById(Long userId);
    
    @Query("SELECT DISTINCT r FROM ResearchPaper r " +
           "WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(r.abstractText) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(r.authors) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(r.summary) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR EXISTS (SELECT t FROM r.tags t WHERE LOWER(t) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<ResearchPaper> searchPapers(@Param("query") String query);

    // Advanced search with filters
    @Query("SELECT DISTINCT r FROM ResearchPaper r " +
           "WHERE (:title IS NULL OR LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
           "AND (:author IS NULL OR LOWER(r.authors) LIKE LOWER(CONCAT('%', :author, '%'))) " +
           "AND (:tag IS NULL OR EXISTS (SELECT t FROM r.tags t WHERE LOWER(t) = LOWER(:tag))) " +
           "AND (:fromDate IS NULL OR r.publishedAt >= :fromDate) " +
           "AND (:toDate IS NULL OR r.publishedAt <= :toDate)")
    List<ResearchPaper> advancedSearch(
            @Param("title") String title,
            @Param("author") String author,
            @Param("tag") String tag,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate);

    // Find papers by multiple tags (ALL tags must match)
    @Query("SELECT r FROM ResearchPaper r " +
           "WHERE (SELECT COUNT(DISTINCT t) FROM r.tags t WHERE LOWER(t) IN :tags) = :tagCount")
    List<ResearchPaper> findByMultipleTags(@Param("tags") List<String> tags, @Param("tagCount") long tagCount);

    // Find similar papers based on tags
    @Query("SELECT DISTINCT r2, " +
           "(SELECT COUNT(t) FROM r2.tags t WHERE t IN (SELECT t2 FROM r1.tags t2)) as matchCount " +
           "FROM ResearchPaper r1, ResearchPaper r2 " +
           "WHERE r1.id = :paperId " +
           "AND r2.id <> r1.id " +
           "AND EXISTS (SELECT t FROM r2.tags t WHERE t IN (SELECT t2 FROM r1.tags t2)) " +
           "ORDER BY matchCount DESC")
    List<ResearchPaper> findSimilarPapers(@Param("paperId") Long paperId);
    
    List<ResearchPaper> findByTagsContainingIgnoreCase(String tag);
}
