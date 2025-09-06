package com.mbbank.biz.pro.service;

import com.mbbank.biz.pro.domain.*; // for static metamodels
import com.mbbank.biz.pro.domain.SensitiveInfo;
import com.mbbank.biz.pro.repository.SensitiveInfoRepository;
import com.mbbank.biz.pro.service.criteria.SensitiveInfoCriteria;
import com.mbbank.biz.pro.service.dto.SensitiveInfoDTO;
import com.mbbank.biz.pro.service.mapper.SensitiveInfoMapper;
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
 * Service for executing complex queries for {@link SensitiveInfo} entities in the database.
 * The main input is a {@link SensitiveInfoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SensitiveInfoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SensitiveInfoQueryService extends QueryService<SensitiveInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(SensitiveInfoQueryService.class);

    private final SensitiveInfoRepository sensitiveInfoRepository;

    private final SensitiveInfoMapper sensitiveInfoMapper;

    public SensitiveInfoQueryService(SensitiveInfoRepository sensitiveInfoRepository, SensitiveInfoMapper sensitiveInfoMapper) {
        this.sensitiveInfoRepository = sensitiveInfoRepository;
        this.sensitiveInfoMapper = sensitiveInfoMapper;
    }

    /**
     * Return a {@link Page} of {@link SensitiveInfoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SensitiveInfoDTO> findByCriteria(SensitiveInfoCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SensitiveInfo> specification = createSpecification(criteria);
        return sensitiveInfoRepository.findAll(specification, page).map(sensitiveInfoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SensitiveInfoCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<SensitiveInfo> specification = createSpecification(criteria);
        return sensitiveInfoRepository.count(specification);
    }

    /**
     * Function to convert {@link SensitiveInfoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SensitiveInfo> createSpecification(SensitiveInfoCriteria criteria) {
        Specification<SensitiveInfo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), SensitiveInfo_.id),
                buildStringSpecification(criteria.getInfoType(), SensitiveInfo_.infoType),
                buildRangeSpecification(criteria.getPageNumber(), SensitiveInfo_.pageNumber),
                buildStringSpecification(criteria.getPosition(), SensitiveInfo_.position),
                buildRangeSpecification(criteria.getDetectedAt(), SensitiveInfo_.detectedAt),
                buildSpecification(criteria.getDocumentId(), root -> root.join(SensitiveInfo_.document, JoinType.LEFT).get(Document_.id))
            );
        }
        return specification;
    }
}
