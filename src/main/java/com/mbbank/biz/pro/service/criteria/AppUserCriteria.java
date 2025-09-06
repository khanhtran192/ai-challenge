package com.mbbank.biz.pro.service.criteria;

import com.mbbank.biz.pro.domain.enumeration.Role;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mbbank.biz.pro.domain.AppUser} entity. This class is used
 * in {@link com.mbbank.biz.pro.web.rest.AppUserResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /app-users?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserCriteria implements Serializable, Criteria {

    /**
     * Class for filtering Role
     */
    public static class RoleFilter extends Filter<Role> {

        public RoleFilter() {}

        public RoleFilter(RoleFilter filter) {
            super(filter);
        }

        @Override
        public RoleFilter copy() {
            return new RoleFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter email;

    private StringFilter passwordHash;

    private RoleFilter role;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter documentsId;

    private LongFilter sharesId;

    private LongFilter auditLogsId;

    private Boolean distinct;

    public AppUserCriteria() {}

    public AppUserCriteria(AppUserCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.fullName = other.optionalFullName().map(StringFilter::copy).orElse(null);
        this.email = other.optionalEmail().map(StringFilter::copy).orElse(null);
        this.passwordHash = other.optionalPasswordHash().map(StringFilter::copy).orElse(null);
        this.role = other.optionalRole().map(RoleFilter::copy).orElse(null);
        this.createdAt = other.optionalCreatedAt().map(InstantFilter::copy).orElse(null);
        this.updatedAt = other.optionalUpdatedAt().map(InstantFilter::copy).orElse(null);
        this.documentsId = other.optionalDocumentsId().map(LongFilter::copy).orElse(null);
        this.sharesId = other.optionalSharesId().map(LongFilter::copy).orElse(null);
        this.auditLogsId = other.optionalAuditLogsId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AppUserCriteria copy() {
        return new AppUserCriteria(this);
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

    public StringFilter getFullName() {
        return fullName;
    }

    public Optional<StringFilter> optionalFullName() {
        return Optional.ofNullable(fullName);
    }

    public StringFilter fullName() {
        if (fullName == null) {
            setFullName(new StringFilter());
        }
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public Optional<StringFilter> optionalEmail() {
        return Optional.ofNullable(email);
    }

    public StringFilter email() {
        if (email == null) {
            setEmail(new StringFilter());
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPasswordHash() {
        return passwordHash;
    }

    public Optional<StringFilter> optionalPasswordHash() {
        return Optional.ofNullable(passwordHash);
    }

    public StringFilter passwordHash() {
        if (passwordHash == null) {
            setPasswordHash(new StringFilter());
        }
        return passwordHash;
    }

    public void setPasswordHash(StringFilter passwordHash) {
        this.passwordHash = passwordHash;
    }

    public RoleFilter getRole() {
        return role;
    }

    public Optional<RoleFilter> optionalRole() {
        return Optional.ofNullable(role);
    }

    public RoleFilter role() {
        if (role == null) {
            setRole(new RoleFilter());
        }
        return role;
    }

    public void setRole(RoleFilter role) {
        this.role = role;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public Optional<InstantFilter> optionalCreatedAt() {
        return Optional.ofNullable(createdAt);
    }

    public InstantFilter createdAt() {
        if (createdAt == null) {
            setCreatedAt(new InstantFilter());
        }
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public Optional<InstantFilter> optionalUpdatedAt() {
        return Optional.ofNullable(updatedAt);
    }

    public InstantFilter updatedAt() {
        if (updatedAt == null) {
            setUpdatedAt(new InstantFilter());
        }
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LongFilter getDocumentsId() {
        return documentsId;
    }

    public Optional<LongFilter> optionalDocumentsId() {
        return Optional.ofNullable(documentsId);
    }

    public LongFilter documentsId() {
        if (documentsId == null) {
            setDocumentsId(new LongFilter());
        }
        return documentsId;
    }

    public void setDocumentsId(LongFilter documentsId) {
        this.documentsId = documentsId;
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
        final AppUserCriteria that = (AppUserCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(passwordHash, that.passwordHash) &&
            Objects.equals(role, that.role) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(documentsId, that.documentsId) &&
            Objects.equals(sharesId, that.sharesId) &&
            Objects.equals(auditLogsId, that.auditLogsId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, passwordHash, role, createdAt, updatedAt, documentsId, sharesId, auditLogsId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFullName().map(f -> "fullName=" + f + ", ").orElse("") +
            optionalEmail().map(f -> "email=" + f + ", ").orElse("") +
            optionalPasswordHash().map(f -> "passwordHash=" + f + ", ").orElse("") +
            optionalRole().map(f -> "role=" + f + ", ").orElse("") +
            optionalCreatedAt().map(f -> "createdAt=" + f + ", ").orElse("") +
            optionalUpdatedAt().map(f -> "updatedAt=" + f + ", ").orElse("") +
            optionalDocumentsId().map(f -> "documentsId=" + f + ", ").orElse("") +
            optionalSharesId().map(f -> "sharesId=" + f + ", ").orElse("") +
            optionalAuditLogsId().map(f -> "auditLogsId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
