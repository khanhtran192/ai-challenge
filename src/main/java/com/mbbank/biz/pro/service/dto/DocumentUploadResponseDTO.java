package com.mbbank.biz.pro.service.dto;

import com.mbbank.biz.pro.domain.enumeration.DocumentStatus;
import com.mbbank.biz.pro.domain.enumeration.FileType;
import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for Document upload response.
 */
public class DocumentUploadResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String fileName;
    private FileType fileType;
    private Long fileSize;
    private DocumentStatus status;
    private Instant uploadedAt;
    private AppUserDTO owner;

    public DocumentUploadResponseDTO() {
        // Empty constructor needed for Jackson.
    }

    public DocumentUploadResponseDTO(
        String id,
        String fileName,
        FileType fileType,
        Long fileSize,
        DocumentStatus status,
        Instant uploadedAt,
        AppUserDTO owner
    ) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.status = status;
        this.uploadedAt = uploadedAt;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public AppUserDTO getOwner() {
        return owner;
    }

    public void setOwner(AppUserDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentUploadResponseDTO)) {
            return false;
        }
        return getId() != null && getId().equals(((DocumentUploadResponseDTO) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "DocumentUploadResponseDTO{" +
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
            ", owner=" +
            getOwner() +
            "}"
        );
    }
}
