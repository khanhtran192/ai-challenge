package com.mbbank.biz.pro.service.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for Document list data with pagination.
 */
public class DocumentListDataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<DocumentListResponseDTO> documents;
    private PaginationDTO pagination;

    public DocumentListDataDTO() {
        // Empty constructor needed for Jackson.
    }

    public DocumentListDataDTO(List<DocumentListResponseDTO> documents, PaginationDTO pagination) {
        this.documents = documents;
        this.pagination = pagination;
    }

    public List<DocumentListResponseDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentListResponseDTO> documents) {
        this.documents = documents;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "DocumentListDataDTO{" + "documents=" + documents + ", pagination=" + pagination + "}";
    }
}
