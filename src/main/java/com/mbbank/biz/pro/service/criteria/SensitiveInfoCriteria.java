package com.mbbank.biz.pro.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mbbank.biz.pro.domain.SensitiveInfo} entity. This class is used
 * in {@link com.mbbank.biz.pro.web.rest.SensitiveInfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sensitive-infos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SensitiveInfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter infoType;

    private IntegerFilter pageNumber;

    private StringFilter position;

    private InstantFilter detectedAt;

    private LongFilter documentId;

    private Boolean distinct;

    public SensitiveInfoCriteria() {}

    public SensitiveInfoCriteria(SensitiveInfoCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.infoType = other.optionalInfoType().map(StringFilter::copy).orElse(null);
        this.pageNumber = other.optionalPageNumber().map(IntegerFilter::copy).orElse(null);
        this.position = other.optionalPosition().map(StringFilter::copy).orElse(null);
        this.detectedAt = other.optionalDetectedAt().map(InstantFilter::copy).orElse(null);
        this.documentId = other.optionalDocumentId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SensitiveInfoCriteria copy() {
        return new SensitiveInfoCriteria(this);
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

    public StringFilter getInfoType() {
        return infoType;
    }

    public Optional<StringFilter> optionalInfoType() {
        return Optional.ofNullable(infoType);
    }

    public StringFilter infoType() {
        if (infoType == null) {
            setInfoType(new StringFilter());
        }
        return infoType;
    }

    public void setInfoType(StringFilter infoType) {
        this.infoType = infoType;
    }

    public IntegerFilter getPageNumber() {
        return pageNumber;
    }

    public Optional<IntegerFilter> optionalPageNumber() {
        return Optional.ofNullable(pageNumber);
    }

    public IntegerFilter pageNumber() {
        if (pageNumber == null) {
            setPageNumber(new IntegerFilter());
        }
        return pageNumber;
    }

    public void setPageNumber(IntegerFilter pageNumber) {
        this.pageNumber = pageNumber;
    }

    public StringFilter getPosition() {
        return position;
    }

    public Optional<StringFilter> optionalPosition() {
        return Optional.ofNullable(position);
    }

    public StringFilter position() {
        if (position == null) {
            setPosition(new StringFilter());
        }
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public InstantFilter getDetectedAt() {
        return detectedAt;
    }

    public Optional<InstantFilter> optionalDetectedAt() {
        return Optional.ofNullable(detectedAt);
    }

    public InstantFilter detectedAt() {
        if (detectedAt == null) {
            setDetectedAt(new InstantFilter());
        }
        return detectedAt;
    }

    public void setDetectedAt(InstantFilter detectedAt) {
        this.detectedAt = detectedAt;
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
        final SensitiveInfoCriteria that = (SensitiveInfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(infoType, that.infoType) &&
            Objects.equals(pageNumber, that.pageNumber) &&
            Objects.equals(position, that.position) &&
            Objects.equals(detectedAt, that.detectedAt) &&
            Objects.equals(documentId, that.documentId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, infoType, pageNumber, position, detectedAt, documentId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SensitiveInfoCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInfoType().map(f -> "infoType=" + f + ", ").orElse("") +
            optionalPageNumber().map(f -> "pageNumber=" + f + ", ").orElse("") +
            optionalPosition().map(f -> "position=" + f + ", ").orElse("") +
            optionalDetectedAt().map(f -> "detectedAt=" + f + ", ").orElse("") +
            optionalDocumentId().map(f -> "documentId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
