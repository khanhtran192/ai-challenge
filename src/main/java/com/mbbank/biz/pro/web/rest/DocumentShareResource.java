package com.mbbank.biz.pro.web.rest;

import com.mbbank.biz.pro.repository.DocumentShareRepository;
import com.mbbank.biz.pro.service.DocumentShareQueryService;
import com.mbbank.biz.pro.service.DocumentShareService;
import com.mbbank.biz.pro.service.criteria.DocumentShareCriteria;
import com.mbbank.biz.pro.service.dto.DocumentShareDTO;
import com.mbbank.biz.pro.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mbbank.biz.pro.domain.DocumentShare}.
 */
@RestController
@RequestMapping("/api/document-shares")
public class DocumentShareResource {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentShareResource.class);

    private static final String ENTITY_NAME = "documentShare";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentShareService documentShareService;

    private final DocumentShareRepository documentShareRepository;

    private final DocumentShareQueryService documentShareQueryService;

    public DocumentShareResource(
        DocumentShareService documentShareService,
        DocumentShareRepository documentShareRepository,
        DocumentShareQueryService documentShareQueryService
    ) {
        this.documentShareService = documentShareService;
        this.documentShareRepository = documentShareRepository;
        this.documentShareQueryService = documentShareQueryService;
    }

    /**
     * {@code POST  /document-shares} : Create a new documentShare.
     *
     * @param documentShareDTO the documentShareDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentShareDTO, or with status {@code 400 (Bad Request)} if the documentShare has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DocumentShareDTO> createDocumentShare(@Valid @RequestBody DocumentShareDTO documentShareDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DocumentShare : {}", documentShareDTO);
        if (documentShareDTO.getId() != null) {
            throw new BadRequestAlertException("A new documentShare cannot already have an ID", ENTITY_NAME, "idexists");
        }
        documentShareDTO = documentShareService.save(documentShareDTO);
        return ResponseEntity.created(new URI("/api/document-shares/" + documentShareDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, documentShareDTO.getId().toString()))
            .body(documentShareDTO);
    }

    /**
     * {@code PUT  /document-shares/:id} : Updates an existing documentShare.
     *
     * @param id the id of the documentShareDTO to save.
     * @param documentShareDTO the documentShareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentShareDTO,
     * or with status {@code 400 (Bad Request)} if the documentShareDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentShareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DocumentShareDTO> updateDocumentShare(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody DocumentShareDTO documentShareDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DocumentShare : {}, {}", id, documentShareDTO);
        if (documentShareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentShareDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentShareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        documentShareDTO = documentShareService.update(documentShareDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentShareDTO.getId().toString()))
            .body(documentShareDTO);
    }

    /**
     * {@code PATCH  /document-shares/:id} : Partial updates given fields of an existing documentShare, field will ignore if it is null
     *
     * @param id the id of the documentShareDTO to save.
     * @param documentShareDTO the documentShareDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentShareDTO,
     * or with status {@code 400 (Bad Request)} if the documentShareDTO is not valid,
     * or with status {@code 404 (Not Found)} if the documentShareDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the documentShareDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DocumentShareDTO> partialUpdateDocumentShare(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody DocumentShareDTO documentShareDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DocumentShare partially : {}, {}", id, documentShareDTO);
        if (documentShareDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, documentShareDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!documentShareRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DocumentShareDTO> result = documentShareService.partialUpdate(documentShareDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentShareDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /document-shares} : get all the documentShares.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentShares in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DocumentShareDTO>> getAllDocumentShares(
        DocumentShareCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get DocumentShares by criteria: {}", criteria);

        Page<DocumentShareDTO> page = documentShareQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /document-shares/count} : count all the documentShares.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countDocumentShares(DocumentShareCriteria criteria) {
        LOG.debug("REST request to count DocumentShares by criteria: {}", criteria);
        return ResponseEntity.ok().body(documentShareQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /document-shares/:id} : get the "id" documentShare.
     *
     * @param id the id of the documentShareDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentShareDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DocumentShareDTO> getDocumentShare(@PathVariable("id") String id) {
        LOG.debug("REST request to get DocumentShare : {}", id);
        Optional<DocumentShareDTO> documentShareDTO = documentShareService.findOne(id);
        return ResponseUtil.wrapOrNotFound(documentShareDTO);
    }

    /**
     * {@code DELETE  /document-shares/:id} : delete the "id" documentShare.
     *
     * @param id the id of the documentShareDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentShare(@PathVariable("id") String id) {
        LOG.debug("REST request to delete DocumentShare : {}", id);
        documentShareService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
