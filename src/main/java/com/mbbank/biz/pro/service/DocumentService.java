package com.mbbank.biz.pro.service;

import com.mbbank.biz.pro.domain.AppUser;
import com.mbbank.biz.pro.domain.Document;
import com.mbbank.biz.pro.domain.enumeration.DocumentStatus;
import com.mbbank.biz.pro.domain.enumeration.FileType;
import com.mbbank.biz.pro.repository.DocumentRepository;
import com.mbbank.biz.pro.service.dto.ApiResponseDTO;
import com.mbbank.biz.pro.service.dto.DocumentDTO;
import com.mbbank.biz.pro.service.dto.DocumentListDataDTO;
import com.mbbank.biz.pro.service.dto.DocumentListResponseDTO;
import com.mbbank.biz.pro.service.dto.DocumentUploadResponseDTO;
import com.mbbank.biz.pro.service.dto.PaginationDTO;
import com.mbbank.biz.pro.service.mapper.DocumentMapper;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link com.mbbank.biz.pro.domain.Document}.
 */
@Service
@Transactional
public class DocumentService {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);

    private final DocumentRepository documentRepository;

    private final DocumentMapper documentMapper;

    private final FileStorageService fileStorageService;

    private final AuditLogService auditLogService;

    public DocumentService(
        DocumentRepository documentRepository,
        DocumentMapper documentMapper,
        FileStorageService fileStorageService,
        AuditLogService auditLogService
    ) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
        this.fileStorageService = fileStorageService;
        this.auditLogService = auditLogService;
    }

    /**
     * Save a document.
     *
     * @param documentDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentDTO save(DocumentDTO documentDTO) {
        LOG.debug("Request to save Document : {}", documentDTO);
        Document document = documentMapper.toEntity(documentDTO);
        document = documentRepository.save(document);
        return documentMapper.toDto(document);
    }

    /**
     * Update a document.
     *
     * @param documentDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentDTO update(DocumentDTO documentDTO) {
        LOG.debug("Request to update Document : {}", documentDTO);
        Document document = documentMapper.toEntity(documentDTO);
        document = documentRepository.save(document);
        return documentMapper.toDto(document);
    }

    /**
     * Partially update a document.
     *
     * @param documentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DocumentDTO> partialUpdate(DocumentDTO documentDTO) {
        LOG.debug("Request to partially update Document : {}", documentDTO);

        return documentRepository
            .findById(documentDTO.getId())
            .map(existingDocument -> {
                documentMapper.partialUpdate(existingDocument, documentDTO);

                return existingDocument;
            })
            .map(documentRepository::save)
            .map(documentMapper::toDto);
    }

    /**
     * Get one document by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentDTO> findOne(Long id) {
        LOG.debug("Request to get Document : {}", id);
        return documentRepository.findById(id).map(documentMapper::toDto);
    }

    /**
     * Delete the document by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Document : {}", id);
        documentRepository.deleteById(id);
    }

    /**
     * Upload document file.
     *
     * @param file the uploaded file
     * @param currentUser the current user
     * @return upload response
     */
    public ApiResponseDTO<DocumentUploadResponseDTO> uploadDocument(MultipartFile file, AppUser currentUser) {
        LOG.debug("Request to upload document by user: {}", currentUser.getId());

        // Validate file
        ApiResponseDTO.ErrorDTO validationError = fileStorageService.validateFile(file);
        if (validationError != null) {
            return ApiResponseDTO.error(validationError);
        }

        try {
            // Store file
            String storagePath = fileStorageService.storeFile(file);

            // Get file type
            FileType fileType = fileStorageService.getFileType(file.getContentType(), file.getOriginalFilename());

            // Create document entity
            Document document = new Document();
            document.setFileName(file.getOriginalFilename());
            document.setFileType(fileType);
            document.setFileSize(file.getSize());
            document.setStatus(DocumentStatus.uploaded);
            document.setStoragePath(storagePath);
            document.setUploadedAt(Instant.now());
            document.setOwner(currentUser);

            // Save document
            document = documentRepository.save(document);

            // Log audit
            auditLogService.logAction(currentUser, "DOCUMENT_UPLOADED", "Uploaded file: " + file.getOriginalFilename(), document);

            // TODO: Add to scanning queue (will be implemented in Use Case 3)

            // Create response DTO
            DocumentUploadResponseDTO responseDTO = new DocumentUploadResponseDTO(
                document.getId(),
                document.getFileName(),
                document.getFileType(),
                document.getFileSize(),
                document.getStatus(),
                document.getUploadedAt(),
                documentMapper.toDtoAppUserId(currentUser)
            );

            return ApiResponseDTO.success(responseDTO, "Upload tài liệu thành công. Đang bắt đầu quá trình rà quét.");
        } catch (IOException e) {
            LOG.error("Error storing file", e);
            return ApiResponseDTO.error("STORAGE_ERROR", "Lỗi lưu trữ file. Vui lòng thử lại.");
        }
    }

    /**
     * Get documents for current user with pagination.
     *
     * @param currentUser the current user
     * @param pageable pagination parameters
     * @param status optional status filter
     * @return paginated document list
     */
    public ApiResponseDTO<DocumentListDataDTO> getMyDocuments(AppUser currentUser, Pageable pageable, DocumentStatus status) {
        LOG.debug("Request to get documents for user: {}", currentUser.getId());

        Page<Document> documentsPage;
        if (status != null) {
            documentsPage = documentRepository.findByOwnerAndStatusOrderByUploadedAtDesc(currentUser, status, pageable);
        } else {
            documentsPage = documentRepository.findByOwnerOrderByUploadedAtDesc(currentUser, pageable);
        }

        // Convert to response DTOs
        List<DocumentListResponseDTO> documentDTOs = documentsPage.getContent().stream().map(this::convertToListResponseDTO).toList();

        // Create pagination info
        PaginationDTO pagination = new PaginationDTO(
            documentsPage.getNumber(),
            documentsPage.getSize(),
            documentsPage.getTotalElements(),
            documentsPage.getTotalPages()
        );

        DocumentListDataDTO data = new DocumentListDataDTO(documentDTOs, pagination);
        return ApiResponseDTO.success(data);
    }

    private DocumentListResponseDTO convertToListResponseDTO(Document document) {
        // Count sensitive info (will be properly implemented when SensitiveInfo relationship is ready)
        boolean hasSensitiveData = document.getStatus() == DocumentStatus.sensitive;
        int sensitiveCount = hasSensitiveData ? document.getSensitives().size() : 0;

        return new DocumentListResponseDTO(
            document.getId(),
            document.getFileName(),
            document.getFileType(),
            document.getFileSize(),
            document.getStatus(),
            document.getUploadedAt(),
            hasSensitiveData,
            sensitiveCount
        );
    }
}
