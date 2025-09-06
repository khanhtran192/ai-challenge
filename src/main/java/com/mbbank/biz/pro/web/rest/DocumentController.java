package com.mbbank.biz.pro.web.rest;

import com.mbbank.biz.pro.domain.AppUser;
import com.mbbank.biz.pro.domain.enumeration.DocumentStatus;
import com.mbbank.biz.pro.repository.AppUserRepository;
import com.mbbank.biz.pro.security.SecurityUtils;
import com.mbbank.biz.pro.service.DocumentService;
import com.mbbank.biz.pro.service.dto.ApiResponseDTO;
import com.mbbank.biz.pro.service.dto.DocumentListDataDTO;
import com.mbbank.biz.pro.service.dto.DocumentUploadResponseDTO;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing Documents.
 */
@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);

    private final DocumentService documentService;
    private final AppUserRepository appUserRepository;

    public DocumentController(DocumentService documentService, AppUserRepository appUserRepository) {
        this.documentService = documentService;
        this.appUserRepository = appUserRepository;
    }

    /**
     * {@code POST /upload} : Upload a document file.
     *
     * @param file the file to upload
     * @return the ResponseEntity with status 201 (Created) and the uploaded document data,
     *         or with status 400 (Bad Request) if the file is invalid
     */
    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDTO<DocumentUploadResponseDTO>> uploadDocument(@RequestParam("file") MultipartFile file) {
        LOG.debug("REST request to upload document");

        // Get current user
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponseDTO.error("UNAUTHORIZED", "Người dùng chưa đăng nhập"));
        }

        Optional<AppUser> currentUser = appUserRepository.findOneByEmail(currentUserLogin.get());
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiResponseDTO.error("USER_NOT_FOUND", "Không tìm thấy thông tin người dùng")
            );
        }

        // Upload document
        ApiResponseDTO<DocumentUploadResponseDTO> result = documentService.uploadDocument(file, currentUser.get());

        if (result.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            // Determine HTTP status based on error code
            HttpStatus status =
                switch (result.getError().getCode()) {
                    case "INVALID_FILE_TYPE", "EMPTY_FILE" -> HttpStatus.BAD_REQUEST;
                    case "FILE_TOO_LARGE" -> HttpStatus.PAYLOAD_TOO_LARGE;
                    default -> HttpStatus.INTERNAL_SERVER_ERROR;
                };
            return ResponseEntity.status(status).body(result);
        }
    }

    /**
     * {@code GET /my-documents} : Get all documents for the current user.
     *
     * @param page the page number (default: 0)
     * @param size the page size (default: 20)
     * @param status optional status filter
     * @param sortBy sort field (default: uploadedAt)
     * @param sortDir sort direction (default: desc)
     * @return the ResponseEntity with status 200 (OK) and the list of documents in body
     */
    @GetMapping("/my-documents")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponseDTO<DocumentListDataDTO>> getMyDocuments(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(required = false) DocumentStatus status,
        @RequestParam(defaultValue = "uploadedAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir
    ) {
        LOG.debug("REST request to get my documents: page={}, size={}, status={}", page, size, status);

        // Get current user
        Optional<String> currentUserLogin = SecurityUtils.getCurrentUserLogin();
        if (currentUserLogin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponseDTO.error("UNAUTHORIZED", "Người dùng chưa đăng nhập"));
        }

        Optional<AppUser> currentUser = appUserRepository.findOneByEmail(currentUserLogin.get());
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ApiResponseDTO.error("USER_NOT_FOUND", "Không tìm thấy thông tin người dùng")
            );
        }

        // Create pageable
        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Get documents
        ApiResponseDTO<DocumentListDataDTO> result = documentService.getMyDocuments(currentUser.get(), pageable, status);

        return ResponseEntity.ok(result);
    }
}
