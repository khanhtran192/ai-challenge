package com.mbbank.biz.pro.service.dto;

import java.io.Serializable;

/**
 * DTO for pagination information.
 */
public class PaginationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public PaginationDTO() {
        // Empty constructor needed for Jackson.
    }

    public PaginationDTO(int page, int size, long totalElements, int totalPages) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return (
            "PaginationDTO{" + "page=" + page + ", size=" + size + ", totalElements=" + totalElements + ", totalPages=" + totalPages + "}"
        );
    }
}
