package com.mbbank.biz.pro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mbbank.biz.pro.domain.enumeration.DocumentStatus;
import com.mbbank.biz.pro.domain.enumeration.FileType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A Document.
 */
@Entity
@Table(name = "DOCUMENTS")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @NotNull
    @Size(max = 255)
    @Column(name = "file_name", length = 255, nullable = false)
    private String fileName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    @NotNull
    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DocumentStatus status;

    @Lob
    @Column(name = "storage_path", nullable = false)
    private String storagePath;

    @Column(name = "uploaded_at")
    private Instant uploadedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    @JsonIgnoreProperties(value = { "document" }, allowSetters = true)
    private Set<SensitiveInfo> sensitives = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    @JsonIgnoreProperties(value = { "document", "user" }, allowSetters = true)
    private Set<DocumentShare> shares = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "document")
    @JsonIgnoreProperties(value = { "user", "document" }, allowSetters = true)
    private Set<AuditLog> auditLogs = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "documents", "shares", "auditLogs" }, allowSetters = true)
    private AppUser owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Document id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Document fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileType getFileType() {
        return this.fileType;
    }

    public Document fileType(FileType fileType) {
        this.setFileType(fileType);
        return this;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public Document fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public DocumentStatus getStatus() {
        return this.status;
    }

    public Document status(DocumentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public String getStoragePath() {
        return this.storagePath;
    }

    public Document storagePath(String storagePath) {
        this.setStoragePath(storagePath);
        return this;
    }

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public Instant getUploadedAt() {
        return this.uploadedAt;
    }

    public Document uploadedAt(Instant uploadedAt) {
        this.setUploadedAt(uploadedAt);
        return this;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Set<SensitiveInfo> getSensitives() {
        return this.sensitives;
    }

    public void setSensitives(Set<SensitiveInfo> sensitiveInfos) {
        if (this.sensitives != null) {
            this.sensitives.forEach(i -> i.setDocument(null));
        }
        if (sensitiveInfos != null) {
            sensitiveInfos.forEach(i -> i.setDocument(this));
        }
        this.sensitives = sensitiveInfos;
    }

    public Document sensitives(Set<SensitiveInfo> sensitiveInfos) {
        this.setSensitives(sensitiveInfos);
        return this;
    }

    public Document addSensitives(SensitiveInfo sensitiveInfo) {
        this.sensitives.add(sensitiveInfo);
        sensitiveInfo.setDocument(this);
        return this;
    }

    public Document removeSensitives(SensitiveInfo sensitiveInfo) {
        this.sensitives.remove(sensitiveInfo);
        sensitiveInfo.setDocument(null);
        return this;
    }

    public Set<DocumentShare> getShares() {
        return this.shares;
    }

    public void setShares(Set<DocumentShare> documentShares) {
        if (this.shares != null) {
            this.shares.forEach(i -> i.setDocument(null));
        }
        if (documentShares != null) {
            documentShares.forEach(i -> i.setDocument(this));
        }
        this.shares = documentShares;
    }

    public Document shares(Set<DocumentShare> documentShares) {
        this.setShares(documentShares);
        return this;
    }

    public Document addShares(DocumentShare documentShare) {
        this.shares.add(documentShare);
        documentShare.setDocument(this);
        return this;
    }

    public Document removeShares(DocumentShare documentShare) {
        this.shares.remove(documentShare);
        documentShare.setDocument(null);
        return this;
    }

    public Set<AuditLog> getAuditLogs() {
        return this.auditLogs;
    }

    public void setAuditLogs(Set<AuditLog> auditLogs) {
        if (this.auditLogs != null) {
            this.auditLogs.forEach(i -> i.setDocument(null));
        }
        if (auditLogs != null) {
            auditLogs.forEach(i -> i.setDocument(this));
        }
        this.auditLogs = auditLogs;
    }

    public Document auditLogs(Set<AuditLog> auditLogs) {
        this.setAuditLogs(auditLogs);
        return this;
    }

    public Document addAuditLogs(AuditLog auditLog) {
        this.auditLogs.add(auditLog);
        auditLog.setDocument(this);
        return this;
    }

    public Document removeAuditLogs(AuditLog auditLog) {
        this.auditLogs.remove(auditLog);
        auditLog.setDocument(null);
        return this;
    }

    public AppUser getOwner() {
        return this.owner;
    }

    public void setOwner(AppUser appUser) {
        this.owner = appUser;
    }

    public Document owner(AppUser appUser) {
        this.setOwner(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return getId() != null && getId().equals(((Document) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", fileSize=" + getFileSize() +
            ", status='" + getStatus() + "'" +
            ", storagePath='" + getStoragePath() + "'" +
            ", uploadedAt='" + getUploadedAt() + "'" +
            "}";
    }
}
