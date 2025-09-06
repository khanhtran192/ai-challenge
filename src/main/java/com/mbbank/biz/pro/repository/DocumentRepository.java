package com.mbbank.biz.pro.repository;

import com.mbbank.biz.pro.domain.AppUser;
import com.mbbank.biz.pro.domain.Document;
import com.mbbank.biz.pro.domain.enumeration.DocumentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Document entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentRepository extends JpaRepository<Document, String>, JpaSpecificationExecutor<Document> {
    /**
     * Find documents by owner ordered by upload date descending.
     *
     * @param owner the document owner
     * @param pageable pagination parameters
     * @return page of documents
     */
    Page<Document> findByOwnerOrderByUploadedAtDesc(AppUser owner, Pageable pageable);

    /**
     * Find documents by owner and status ordered by upload date descending.
     *
     * @param owner the document owner
     * @param status the document status
     * @param pageable pagination parameters
     * @return page of documents
     */
    Page<Document> findByOwnerAndStatusOrderByUploadedAtDesc(AppUser owner, DocumentStatus status, Pageable pageable);
}
