package com.mbbank.biz.pro.service;

import com.mbbank.biz.pro.domain.DocumentShare;
import com.mbbank.biz.pro.repository.DocumentShareRepository;
import com.mbbank.biz.pro.service.dto.DocumentShareDTO;
import com.mbbank.biz.pro.service.mapper.DocumentShareMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mbbank.biz.pro.domain.DocumentShare}.
 */
@Service
@Transactional
public class DocumentShareService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentShareService.class);

    private final DocumentShareRepository documentShareRepository;

    private final DocumentShareMapper documentShareMapper;

    public DocumentShareService(DocumentShareRepository documentShareRepository, DocumentShareMapper documentShareMapper) {
        this.documentShareRepository = documentShareRepository;
        this.documentShareMapper = documentShareMapper;
    }

    /**
     * Save a documentShare.
     *
     * @param documentShareDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentShareDTO save(DocumentShareDTO documentShareDTO) {
        LOG.debug("Request to save DocumentShare : {}", documentShareDTO);
        DocumentShare documentShare = documentShareMapper.toEntity(documentShareDTO);
        documentShare = documentShareRepository.save(documentShare);
        return documentShareMapper.toDto(documentShare);
    }

    /**
     * Update a documentShare.
     *
     * @param documentShareDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentShareDTO update(DocumentShareDTO documentShareDTO) {
        LOG.debug("Request to update DocumentShare : {}", documentShareDTO);
        DocumentShare documentShare = documentShareMapper.toEntity(documentShareDTO);
        documentShare = documentShareRepository.save(documentShare);
        return documentShareMapper.toDto(documentShare);
    }

    /**
     * Partially update a documentShare.
     *
     * @param documentShareDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentShareDTO> partialUpdate(DocumentShareDTO documentShareDTO) {
        LOG.debug("Request to partially update DocumentShare : {}", documentShareDTO);

        return documentShareRepository
            .findById(documentShareDTO.getId())
            .map(existingDocumentShare -> {
                documentShareMapper.partialUpdate(existingDocumentShare, documentShareDTO);

                return existingDocumentShare;
            })
            .map(documentShareRepository::save)
            .map(documentShareMapper::toDto);
    }

    /**
     * Get one documentShare by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentShareDTO> findOne(Long id) {
        LOG.debug("Request to get DocumentShare : {}", id);
        return documentShareRepository.findById(id).map(documentShareMapper::toDto);
    }

    /**
     * Delete the documentShare by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete DocumentShare : {}", id);
        documentShareRepository.deleteById(id);
    }
}
