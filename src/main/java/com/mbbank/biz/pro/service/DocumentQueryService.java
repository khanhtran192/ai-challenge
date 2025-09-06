package com.mbbank.biz.pro.service;

import com.mbbank.biz.pro.domain.*; // for static metamodels
import com.mbbank.biz.pro.domain.Document;
import com.mbbank.biz.pro.repository.DocumentRepository;
import com.mbbank.biz.pro.service.criteria.DocumentCriteria;
import com.mbbank.biz.pro.service.dto.DocumentDTO;
import com.mbbank.biz.pro.service.mapper.DocumentMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Document} entities in the database.
 * The main input is a {@link DocumentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DocumentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentQueryService extends QueryService<Document> {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentQueryService.class);

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;

    public DocumentQueryService(DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    /**
     * Return a {@link Page} of {@link DocumentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentDTO> findByCriteria(DocumentCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Document> specification = createSpecification(criteria);
        return documentRepository.findAll(specification, page).map(documentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Document> specification = createSpecification(criteria);
        return documentRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Document> createSpecification(DocumentCriteria criteria) {
        Specification<Document> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Document_.id),
                buildStringSpecification(criteria.getFileName(), Document_.fileName),
                buildSpecification(criteria.getFileType(), Document_.fileType),
                buildRangeSpecification(criteria.getFileSize(), Document_.fileSize),
                buildSpecification(criteria.getStatus(), Document_.status),
                buildRangeSpecification(criteria.getUploadedAt(), Document_.uploadedAt),
                buildSpecification(criteria.getSensitivesId(), root -> root.join(Document_.sensitives, JoinType.LEFT).get(SensitiveInfo_.id)
                ),
                buildSpecification(criteria.getSharesId(), root -> root.join(Document_.shares, JoinType.LEFT).get(DocumentShare_.id)),
                buildSpecification(criteria.getAuditLogsId(), root -> root.join(Document_.auditLogs, JoinType.LEFT).get(AuditLog_.id)),
                buildSpecification(criteria.getOwnerId(), root -> root.join(Document_.owner, JoinType.LEFT).get(AppUser_.id))
            );
        }
        return specification;
    }
}
