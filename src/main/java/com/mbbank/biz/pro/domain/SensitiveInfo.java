package com.mbbank.biz.pro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * A SensitiveInfo.
 */
@Entity
@Table(name = "SENSITIVE_INFO")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SensitiveInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @NotNull
    @Size(max = 50)
    @Column(name = "info_type", length = 50, nullable = false)
    private String infoType;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Size(max = 255)
    @Column(name = "position", length = 255)
    private String position;

    @Column(name = "detected_at")
    private Instant detectedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "sensitives", "shares", "auditLogs", "owner" }, allowSetters = true)
    private Document document;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SensitiveInfo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfoType() {
        return this.infoType;
    }

    public SensitiveInfo infoType(String infoType) {
        this.setInfoType(infoType);
        return this;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getContent() {
        return this.content;
    }

    public SensitiveInfo content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getPageNumber() {
        return this.pageNumber;
    }

    public SensitiveInfo pageNumber(Integer pageNumber) {
        this.setPageNumber(pageNumber);
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPosition() {
        return this.position;
    }

    public SensitiveInfo position(String position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Instant getDetectedAt() {
        return this.detectedAt;
    }

    public SensitiveInfo detectedAt(Instant detectedAt) {
        this.setDetectedAt(detectedAt);
        return this;
    }

    public void setDetectedAt(Instant detectedAt) {
        this.detectedAt = detectedAt;
    }

    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public SensitiveInfo document(Document document) {
        this.setDocument(document);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SensitiveInfo)) {
            return false;
        }
        return getId() != null && getId().equals(((SensitiveInfo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SensitiveInfo{" +
            "id=" + getId() +
            ", infoType='" + getInfoType() + "'" +
            ", content='" + getContent() + "'" +
            ", pageNumber=" + getPageNumber() +
            ", position='" + getPosition() + "'" +
            ", detectedAt='" + getDetectedAt() + "'" +
            "}";
    }
}
