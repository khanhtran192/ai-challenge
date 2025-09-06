package com.mbbank.biz.pro.service.dto;

import com.mbbank.biz.pro.domain.enumeration.DocumentStatus;
import com.mbbank.biz.pro.domain.enumeration.FileType;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for Document list response.
 */
public class DocumentListResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String fileName;
    private FileType fileType;
    private Long fileSize;
    private DocumentStatus status;
    private Instant uploadedAt;
    private Boolean hasSensitiveData;
    private Integer sensitiveCount;

    public DocumentListResponseDTO() {
        // Empty constructor needed for Jackson.
    }

    public DocumentListResponseDTO(
        Long id,
        String fileName,
        FileType fileType,
        Long fileSize,
        DocumentStatus status,
        Instant uploadedAt,
        Boolean hasSensitiveData,
        Integer sensitiveCount
    ) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.status = status;
        this.uploadedAt = uploadedAt;
        this.hasSensitiveData = hasSensitiveData;
        this.sensitiveCount = sensitiveCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Boolean getHasSensitiveData() {
        return hasSensitiveData;
    }

    public void setHasSensitiveData(Boolean hasSensitiveData) {
        this.hasSensitiveData = hasSensitiveData;
    }

    public Integer getSensitiveCount() {
        return sensitiveCount;
    }

    public void setSensitiveCount(Integer sensitiveCount) {
        this.sensitiveCount = sensitiveCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentListResponseDTO)) {
            return false;
        }
        return getId() != null && getId().equals(((DocumentListResponseDTO) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "DocumentListResponseDTO{" +
            "id=" +
            getId() +
            ", fileName='" +
            getFileName() +
            "'" +
            ", fileType='" +
            getFileType() +
            "'" +
            ", fileSize=" +
            getFileSize() +
            ", status='" +
            getStatus() +
            "'" +
            ", uploadedAt='" +
            getUploadedAt() +
            "'" +
            ", hasSensitiveData=" +
            getHasSensitiveData() +
            ", sensitiveCount=" +
            getSensitiveCount() +
            "}"
        );
    }
}
