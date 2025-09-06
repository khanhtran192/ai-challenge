package com.mbbank.biz.pro.service;

import com.mbbank.biz.pro.domain.*; // for static metamodels
import com.mbbank.biz.pro.domain.DocumentShare;
import com.mbbank.biz.pro.repository.DocumentShareRepository;
import com.mbbank.biz.pro.service.criteria.DocumentShareCriteria;
import com.mbbank.biz.pro.service.dto.DocumentShareDTO;
import com.mbbank.biz.pro.service.mapper.DocumentShareMapper;
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
 * Service for executing complex queries for {@link DocumentShare} entities in the database.
 * The main input is a {@link DocumentShareCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DocumentShareDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentShareQueryService extends QueryService<DocumentShare> {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentShareQueryService.class);

    private final DocumentShareRepository documentShareRepository;

    private final DocumentShareMapper documentShareMapper;

    public DocumentShareQueryService(DocumentShareRepository documentShareRepository, DocumentShareMapper documentShareMapper) {
        this.documentShareRepository = documentShareRepository;
        this.documentShareMapper = documentShareMapper;
    }

    /**
     * Return a {@link Page} of {@link DocumentShareDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentShareDTO> findByCriteria(DocumentShareCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DocumentShare> specification = createSpecification(criteria);
        return documentShareRepository.findAll(specification, page).map(documentShareMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentShareCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<DocumentShare> specification = createSpecification(criteria);
        return documentShareRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentShareCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DocumentShare> createSpecification(DocumentShareCriteria criteria) {
        Specification<DocumentShare> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), DocumentShare_.id),
                buildSpecification(criteria.getPermission(), DocumentShare_.permission),
                buildRangeSpecification(criteria.getGrantedAt(), DocumentShare_.grantedAt),
                buildSpecification(criteria.getDocumentId(), root -> root.join(DocumentShare_.document, JoinType.LEFT).get(Document_.id)),
                buildSpecification(criteria.getUserId(), root -> root.join(DocumentShare_.user, JoinType.LEFT).get(AppUser_.id))
            );
        }
        return specification;
    }
}
