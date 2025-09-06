package com.mbbank.biz.pro.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mbbank.biz.pro.domain.SensitiveInfo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SensitiveInfoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String infoType;

    @Lob
    private String content;

    private Integer pageNumber;

    @Size(max = 255)
    private String position;

    private Instant detectedAt;

    @NotNull
    private DocumentDTO document;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Instant getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(Instant detectedAt) {
        this.detectedAt = detectedAt;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SensitiveInfoDTO)) {
            return false;
        }

        SensitiveInfoDTO sensitiveInfoDTO = (SensitiveInfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sensitiveInfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SensitiveInfoDTO{" +
            "id=" + getId() +
            ", infoType='" + getInfoType() + "'" +
            ", content='" + getContent() + "'" +
            ", pageNumber=" + getPageNumber() +
            ", position='" + getPosition() + "'" +
            ", detectedAt='" + getDetectedAt() + "'" +
            ", document=" + getDocument() +
            "}";
    }
}
