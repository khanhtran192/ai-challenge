package com.mbbank.biz.pro.service.criteria;

import com.mbbank.biz.pro.domain.enumeration.SharePermission;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mbbank.biz.pro.domain.DocumentShare} entity. This class is used
 * in {@link com.mbbank.biz.pro.web.rest.DocumentShareResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /document-shares?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentShareCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SharePermission
     */
    public static class SharePermissionFilter extends Filter<SharePermission> {

        public SharePermissionFilter() {}

        public SharePermissionFilter(SharePermissionFilter filter) {
            super(filter);
        }

        @Override
        public SharePermissionFilter copy() {
            return new SharePermissionFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SharePermissionFilter permission;

    private InstantFilter grantedAt;

    private LongFilter documentId;

    private LongFilter userId;

    private Boolean distinct;

    public DocumentShareCriteria() {}

    public DocumentShareCriteria(DocumentShareCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.permission = other.optionalPermission().map(SharePermissionFilter::copy).orElse(null);
        this.grantedAt = other.optionalGrantedAt().map(InstantFilter::copy).orElse(null);
        this.documentId = other.optionalDocumentId().map(LongFilter::copy).orElse(null);
        this.userId = other.optionalUserId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public DocumentShareCriteria copy() {
        return new DocumentShareCriteria(this);
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

    public SharePermissionFilter getPermission() {
        return permission;
    }

    public Optional<SharePermissionFilter> optionalPermission() {
        return Optional.ofNullable(permission);
    }

    public SharePermissionFilter permission() {
        if (permission == null) {
            setPermission(new SharePermissionFilter());
        }
        return permission;
    }

    public void setPermission(SharePermissionFilter permission) {
        this.permission = permission;
    }

    public InstantFilter getGrantedAt() {
        return grantedAt;
    }

    public Optional<InstantFilter> optionalGrantedAt() {
        return Optional.ofNullable(grantedAt);
    }

    public InstantFilter grantedAt() {
        if (grantedAt == null) {
            setGrantedAt(new InstantFilter());
        }
        return grantedAt;
    }

    public void setGrantedAt(InstantFilter grantedAt) {
        this.grantedAt = grantedAt;
    }

    public LongFilter getDocumentId() {
        return documentId;
    }

    public Optional<LongFilter> optionalDocumentId() {
        return Optional.ofNullable(documentId);
    }

    public LongFilter documentId() {
        if (documentId == null) {
            setDocumentId(new LongFilter());
        }
        return documentId;
    }

    public void setDocumentId(LongFilter documentId) {
        this.documentId = documentId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public Optional<LongFilter> optionalUserId() {
        return Optional.ofNullable(userId);
    }

    public LongFilter userId() {
        if (userId == null) {
            setUserId(new LongFilter());
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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
        final DocumentShareCriteria that = (DocumentShareCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(permission, that.permission) &&
            Objects.equals(grantedAt, that.grantedAt) &&
            Objects.equals(documentId, that.documentId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, permission, grantedAt, documentId, userId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentShareCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalPermission().map(f -> "permission=" + f + ", ").orElse("") +
            optionalGrantedAt().map(f -> "grantedAt=" + f + ", ").orElse("") +
            optionalDocumentId().map(f -> "documentId=" + f + ", ").orElse("") +
            optionalUserId().map(f -> "userId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
