package com.mbbank.biz.pro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mbbank.biz.pro.domain.enumeration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A AppUser.
 */
@Entity
@Table(name = "USERS")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @NotNull
    @Size(min = 60, max = 255)
    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    @JsonIgnoreProperties(value = { "sensitives", "shares", "auditLogs", "owner" }, allowSetters = true)
    private Set<Document> documents = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnoreProperties(value = { "document", "user" }, allowSetters = true)
    private Set<DocumentShare> shares = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonIgnoreProperties(value = { "user", "document" }, allowSetters = true)
    private Set<AuditLog> auditLogs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AppUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public AppUser fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public AppUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return this.passwordHash;
    }

    public AppUser passwordHash(String passwordHash) {
        this.setPasswordHash(passwordHash);
        return this;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return this.role;
    }

    public AppUser role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public AppUser createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public AppUser updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set<Document> documents) {
        if (this.documents != null) {
            this.documents.forEach(i -> i.setOwner(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setOwner(this));
        }
        this.documents = documents;
    }

    public AppUser documents(Set<Document> documents) {
        this.setDocuments(documents);
        return this;
    }

    public AppUser addDocuments(Document document) {
        this.documents.add(document);
        document.setOwner(this);
        return this;
    }

    public AppUser removeDocuments(Document document) {
        this.documents.remove(document);
        document.setOwner(null);
        return this;
    }

    public Set<DocumentShare> getShares() {
        return this.shares;
    }

    public void setShares(Set<DocumentShare> documentShares) {
        if (this.shares != null) {
            this.shares.forEach(i -> i.setUser(null));
        }
        if (documentShares != null) {
            documentShares.forEach(i -> i.setUser(this));
        }
        this.shares = documentShares;
    }

    public AppUser shares(Set<DocumentShare> documentShares) {
        this.setShares(documentShares);
        return this;
    }

    public AppUser addShares(DocumentShare documentShare) {
        this.shares.add(documentShare);
        documentShare.setUser(this);
        return this;
    }

    public AppUser removeShares(DocumentShare documentShare) {
        this.shares.remove(documentShare);
        documentShare.setUser(null);
        return this;
    }

    public Set<AuditLog> getAuditLogs() {
        return this.auditLogs;
    }

    public void setAuditLogs(Set<AuditLog> auditLogs) {
        if (this.auditLogs != null) {
            this.auditLogs.forEach(i -> i.setUser(null));
        }
        if (auditLogs != null) {
            auditLogs.forEach(i -> i.setUser(this));
        }
        this.auditLogs = auditLogs;
    }

    public AppUser auditLogs(Set<AuditLog> auditLogs) {
        this.setAuditLogs(auditLogs);
        return this;
    }

    public AppUser addAuditLogs(AuditLog auditLog) {
        this.auditLogs.add(auditLog);
        auditLog.setUser(this);
        return this;
    }

    public AppUser removeAuditLogs(AuditLog auditLog) {
        this.auditLogs.remove(auditLog);
        auditLog.setUser(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUser)) {
            return false;
        }
        return getId() != null && getId().equals(((AppUser) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUser{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", email='" + getEmail() + "'" +
            ", passwordHash='" + getPasswordHash() + "'" +
            ", role='" + getRole() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
