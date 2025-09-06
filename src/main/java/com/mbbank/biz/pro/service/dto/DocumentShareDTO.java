package com.mbbank.biz.pro.service.dto;

import com.mbbank.biz.pro.domain.enumeration.SharePermission;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mbbank.biz.pro.domain.DocumentShare} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentShareDTO implements Serializable {

    private Long id;

    @NotNull
    private SharePermission permission;

    private Instant grantedAt;

    @NotNull
    private DocumentDTO document;

    @NotNull
    private AppUserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SharePermission getPermission() {
        return permission;
    }

    public void setPermission(SharePermission permission) {
        this.permission = permission;
    }

    public Instant getGrantedAt() {
        return grantedAt;
    }

    public void setGrantedAt(Instant grantedAt) {
        this.grantedAt = grantedAt;
    }

    public DocumentDTO getDocument() {
        return document;
    }

    public void setDocument(DocumentDTO document) {
        this.document = document;
    }

    public AppUserDTO getUser() {
        return user;
    }

    public void setUser(AppUserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentShareDTO)) {
            return false;
        }

        DocumentShareDTO documentShareDTO = (DocumentShareDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentShareDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentShareDTO{" +
            "id=" + getId() +
            ", permission='" + getPermission() + "'" +
            ", grantedAt='" + getGrantedAt() + "'" +
            ", document=" + getDocument() +
            ", user=" + getUser() +
            "}";
    }
}
