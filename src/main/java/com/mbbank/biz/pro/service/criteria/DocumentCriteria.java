package com.mbbank.biz.pro.service.criteria;

import com.mbbank.biz.pro.domain.enumeration.DocumentStatus;
import com.mbbank.biz.pro.domain.enumeration.FileType;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mbbank.biz.pro.domain.Document} entity. This class is used
 * in {@link com.mbbank.biz.pro.web.rest.DocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FileType
     */
    public static class FileTypeFilter extends Filter<FileType> {

        public FileTypeFilter() {}

        public FileTypeFilter(FileTypeFilter filter) {
            super(filter);
        }

        @Override
        public FileTypeFilter copy() {
            return new FileTypeFilter(this);
        }
    }

    /**
     * Class for filtering DocumentStatus
     */
    public static class DocumentStatusFilter extends Filter<DocumentStatus> {

        public DocumentStatusFilter() {}

        public DocumentStatusFilter(DocumentStatusFilter filter) {
            super(filter);
        }

        @Override
        public DocumentStatusFilter copy() {
            return new DocumentStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fileName;

    private FileTypeFilter fileType;

    private LongFilter fileSize;

    private DocumentStatusFilter status;

    private InstantFilter uploadedAt;

    private LongFilter sensitivesId;

    private LongFilter sharesId;

    private LongFilter auditLogsId;

    private LongFilter ownerId;

    private Boolean distinct;

    public DocumentCriteria() {}

    public DocumentCriteria(DocumentCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.fileName = other.optionalFileName().map(StringFilter::copy).orElse(null);
        this.fileType = other.optionalFileType().map(FileTypeFilter::copy).orElse(null);
        this.fileSize = other.optionalFileSize().map(LongFilter::copy).orElse(null);
        this.status = other.optionalStatus().map(DocumentStatusFilter::copy).orElse(null);
        this.uploadedAt = other.optionalUploadedAt().map(InstantFilter::copy).orElse(null);
        this.sensitivesId = other.optionalSensitivesId().map(LongFilter::copy).orElse(null);
        this.sharesId = other.optionalSharesId().map(LongFilter::copy).orElse(null);
        this.auditLogsId = other.optionalAuditLogsId().map(LongFilter::copy).orElse(null);
        this.ownerId = other.optionalOwnerId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DocumentCriteria copy() {
        return new DocumentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public Optional<StringFilter> optionalFileName() {
        return Optional.ofNullable(fileName);
    }

    public StringFilter fileName() {
        if (fileName == null) {
            setFileName(new StringFilter());
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public FileTypeFilter getFileType() {
        return fileType;
    }

    public Optional<FileTypeFilter> optionalFileType() {
        return Optional.ofNullable(fileType);
    }

    public FileTypeFilter fileType() {
        if (fileType == null) {
            setFileType(new FileTypeFilter());
        }
        return fileType;
    }

    public void setFileType(FileTypeFilter fileType) {
        this.fileType = fileType;
    }

    public LongFilter getFileSize() {
        return fileSize;
    }

    public Optional<LongFilter> optionalFileSize() {
        return Optional.ofNullable(fileSize);
    }

    public LongFilter fileSize() {
        if (fileSize == null) {
            setFileSize(new LongFilter());
        }
        return fileSize;
    }

    public void setFileSize(LongFilter fileSize) {
        this.fileSize = fileSize;
    }

    public DocumentStatusFilter getStatus() {
        return status;
    }

    public Optional<DocumentStatusFilter> optionalStatus() {
        return Optional.ofNullable(status);
    }

    public DocumentStatusFilter status() {
        if (status == null) {
            setStatus(new DocumentStatusFilter());
        }
        return status;
    }

    public void setStatus(DocumentStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getUploadedAt() {
        return uploadedAt;
    }

    public Optional<InstantFilter> optionalUploadedAt() {
        return Optional.ofNullable(uploadedAt);
    }

    public InstantFilter uploadedAt() {
        if (uploadedAt == null) {
            setUploadedAt(new InstantFilter());
        }
        return uploadedAt;
    }

    public void setUploadedAt(InstantFilter uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public LongFilter getSensitivesId() {
        return sensitivesId;
    }

    public Optional<LongFilter> optionalSensitivesId() {
        return Optional.ofNullable(sensitivesId);
    }

    public LongFilter sensitivesId() {
        if (sensitivesId == null) {
            setSensitivesId(new LongFilter());
        }
        return sensitivesId;
    }

    public void setSensitivesId(LongFilter sensitivesId) {
        this.sensitivesId = sensitivesId;
    }

    public LongFilter getSharesId() {
        return sharesId;
    }

    public Optional<LongFilter> optionalSharesId() {
        return Optional.ofNullable(sharesId);
    }

    public LongFilter sharesId() {
        if (sharesId == null) {
            setSharesId(new LongFilter());
        }
        return sharesId;
    }

    public void setSharesId(LongFilter sharesId) {
        this.sharesId = sharesId;
    }

    public LongFilter getAuditLogsId() {
        return auditLogsId;
    }

    public Optional<LongFilter> optionalAuditLogsId() {
        return Optional.ofNullable(auditLogsId);
    }

    public LongFilter auditLogsId() {
        if (auditLogsId == null) {
            setAuditLogsId(new LongFilter());
        }
        return auditLogsId;
    }

    public void setAuditLogsId(LongFilter auditLogsId) {
        this.auditLogsId = auditLogsId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public Optional<LongFilter> optionalOwnerId() {
        return Optional.ofNullable(ownerId);
    }

    public LongFilter ownerId() {
        if (ownerId == null) {
            setOwnerId(new LongFilter());
        }
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocumentCriteria that = (DocumentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(fileType, that.fileType) &&
            Objects.equals(fileSize, that.fileSize) &&
            Objects.equals(status, that.status) &&
            Objects.equals(uploadedAt, that.uploadedAt) &&
            Objects.equals(sensitivesId, that.sensitivesId) &&
            Objects.equals(sharesId, that.sharesId) &&
            Objects.equals(auditLogsId, that.auditLogsId) &&
            Objects.equals(ownerId, that.ownerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, fileType, fileSize, status, uploadedAt, sensitivesId, sharesId, auditLogsId, ownerId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFileName().map(f -> "fileName=" + f + ", ").orElse("") +
            optionalFileType().map(f -> "fileType=" + f + ", ").orElse("") +
            optionalFileSize().map(f -> "fileSize=" + f + ", ").orElse("") +
            optionalStatus().map(f -> "status=" + f + ", ").orElse("") +
            optionalUploadedAt().map(f -> "uploadedAt=" + f + ", ").orElse("") +
            optionalSensitivesId().map(f -> "sensitivesId=" + f + ", ").orElse("") +
            optionalSharesId().map(f -> "sharesId=" + f + ", ").orElse("") +
            optionalAuditLogsId().map(f -> "auditLogsId=" + f + ", ").orElse("") +
            optionalOwnerId().map(f -> "ownerId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
