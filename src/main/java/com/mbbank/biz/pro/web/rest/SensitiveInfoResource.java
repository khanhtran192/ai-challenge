package com.mbbank.biz.pro.web.rest;

import com.mbbank.biz.pro.service.SensitiveInfoQueryService;
import com.mbbank.biz.pro.service.SensitiveInfoService;
import com.mbbank.biz.pro.service.criteria.SensitiveInfoCriteria;
import com.mbbank.biz.pro.service.dto.SensitiveInfoDTO;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mbbank.biz.pro.domain.SensitiveInfo}.
 */
@RestController
@RequestMapping("/api/sensitive-infos")
public class SensitiveInfoResource {

    private static final Logger LOG = LoggerFactory.getLogger(SensitiveInfoResource.class);

    private final SensitiveInfoService sensitiveInfoService;

    private final SensitiveInfoQueryService sensitiveInfoQueryService;

    public SensitiveInfoResource(SensitiveInfoService sensitiveInfoService, SensitiveInfoQueryService sensitiveInfoQueryService) {
        this.sensitiveInfoService = sensitiveInfoService;
        this.sensitiveInfoQueryService = sensitiveInfoQueryService;
    }

    /**
     * {@code GET  /sensitive-infos} : get all the sensitiveInfos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sensitiveInfos in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SensitiveInfoDTO>> getAllSensitiveInfos(
        SensitiveInfoCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get SensitiveInfos by criteria: {}", criteria);

        Page<SensitiveInfoDTO> page = sensitiveInfoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sensitive-infos/count} : count all the sensitiveInfos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSensitiveInfos(SensitiveInfoCriteria criteria) {
        LOG.debug("REST request to count SensitiveInfos by criteria: {}", criteria);
        return ResponseEntity.ok().body(sensitiveInfoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sensitive-infos/:id} : get the "id" sensitiveInfo.
     *
     * @param id the id of the sensitiveInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sensitiveInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SensitiveInfoDTO> getSensitiveInfo(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SensitiveInfo : {}", id);
        Optional<SensitiveInfoDTO> sensitiveInfoDTO = sensitiveInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sensitiveInfoDTO);
    }
}
