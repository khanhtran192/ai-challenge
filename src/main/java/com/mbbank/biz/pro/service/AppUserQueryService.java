package com.mbbank.biz.pro.service;

import com.mbbank.biz.pro.domain.*; // for static metamodels
import com.mbbank.biz.pro.domain.AppUser;
import com.mbbank.biz.pro.repository.AppUserRepository;
import com.mbbank.biz.pro.service.criteria.AppUserCriteria;
import com.mbbank.biz.pro.service.dto.AppUserDTO;
import com.mbbank.biz.pro.service.mapper.AppUserMapper;
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
 * Service for executing complex queries for {@link AppUser} entities in the database.
 * The main input is a {@link AppUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link AppUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AppUserQueryService extends QueryService<AppUser> {

    private static final Logger LOG = LoggerFactory.getLogger(AppUserQueryService.class);

    private final AppUserRepository appUserRepository;

    private final AppUserMapper appUserMapper;

    public AppUserQueryService(AppUserRepository appUserRepository, AppUserMapper appUserMapper) {
        this.appUserRepository = appUserRepository;
        this.appUserMapper = appUserMapper;
    }

    /**
     * Return a {@link Page} of {@link AppUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AppUserDTO> findByCriteria(AppUserCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AppUser> specification = createSpecification(criteria);
        return appUserRepository.findAll(specification, page).map(appUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AppUserCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<AppUser> specification = createSpecification(criteria);
        return appUserRepository.count(specification);
    }

    /**
     * Function to convert {@link AppUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<AppUser> createSpecification(AppUserCriteria criteria) {
        Specification<AppUser> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), AppUser_.id),
                buildStringSpecification(criteria.getFullName(), AppUser_.fullName),
                buildStringSpecification(criteria.getEmail(), AppUser_.email),
                buildStringSpecification(criteria.getPasswordHash(), AppUser_.passwordHash),
                buildSpecification(criteria.getRole(), AppUser_.role),
                buildRangeSpecification(criteria.getCreatedAt(), AppUser_.createdAt),
                buildRangeSpecification(criteria.getUpdatedAt(), AppUser_.updatedAt),
                buildSpecification(criteria.getDocumentsId(), root -> root.join(AppUser_.documents, JoinType.LEFT).get(Document_.id)),
                buildSpecification(criteria.getSharesId(), root -> root.join(AppUser_.shares, JoinType.LEFT).get(DocumentShare_.id)),
                buildSpecification(criteria.getAuditLogsId(), root -> root.join(AppUser_.auditLogs, JoinType.LEFT).get(AuditLog_.id))
            );
        }
        return specification;
    }
}
