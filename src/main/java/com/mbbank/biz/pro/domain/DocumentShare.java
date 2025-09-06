package com.mbbank.biz.pro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mbbank.biz.pro.domain.enumeration.SharePermission;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * A DocumentShare.
 */
@Entity
@Table(name = "DOCUMENT_SHARES")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocumentShare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false)
    private SharePermission permission;

    @Column(name = "granted_at")
    private Instant grantedAt;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "sensitives", "shares", "auditLogs", "owner" }, allowSetters = true)
    private Document document;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "documents", "shares", "auditLogs" }, allowSetters = true)
    private AppUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public DocumentShare id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SharePermission getPermission() {
        return this.permission;
    }

    public DocumentShare permission(SharePermission permission) {
        this.setPermission(permission);
        return this;
    }

    public void setPermission(SharePermission permission) {
        this.permission = permission;
    }

    public Instant getGrantedAt() {
        return this.grantedAt;
    }

    public DocumentShare grantedAt(Instant grantedAt) {
        this.setGrantedAt(grantedAt);
        return this;
    }

    public void setGrantedAt(Instant grantedAt) {
        this.grantedAt = grantedAt;
    }

    public Document getDocument() {
        return this.document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public DocumentShare document(Document document) {
        this.setDocument(document);
        return this;
    }

    public AppUser getUser() {
        return this.user;
    }

    public void setUser(AppUser appUser) {
        this.user = appUser;
    }

    public DocumentShare user(AppUser appUser) {
        this.setUser(appUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentShare)) {
            return false;
        }
        return getId() != null && getId().equals(((DocumentShare) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentShare{" +
            "id=" + getId() +
            ", permission='" + getPermission() + "'" +
            ", grantedAt='" + getGrantedAt() + "'" +
            "}";
    }
}
