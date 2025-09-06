package com.mbbank.biz.pro.service;

import com.mbbank.biz.pro.config.ApplicationProperties;
import com.mbbank.biz.pro.domain.enumeration.FileType;
import com.mbbank.biz.pro.service.dto.ApiResponseDTO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service for handling file storage operations.
 */
@Service
public class FileStorageService {

    private static final Logger LOG = LoggerFactory.getLogger(FileStorageService.class);

    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024; // 50MB
    private static final String PDF_CONTENT_TYPE = "application/pdf";
    private static final String DOCX_CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

    private final ApplicationProperties applicationProperties;

    public FileStorageService(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * Validate uploaded file.
     *
     * @param file the uploaded file
     * @return validation result
     */
    public ApiResponseDTO.ErrorDTO validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return new ApiResponseDTO.ErrorDTO("EMPTY_FILE", "File không được để trống");
        }

        // Check file size
        if (file.getSize() > MAX_FILE_SIZE) {
            return new ApiResponseDTO.ErrorDTO("FILE_TOO_LARGE", "Kích thước file không được vượt quá 50MB");
        }

        // Check file type
        String contentType = file.getContentType();
        String originalFilename = file.getOriginalFilename();

        if (!isValidFileType(contentType, originalFilename)) {
            return new ApiResponseDTO.ErrorDTO("INVALID_FILE_TYPE", "Chỉ chấp nhận file PDF hoặc DOCX");
        }

        return null; // No error
    }

    /**
     * Store uploaded file.
     *
     * @param file the uploaded file
     * @return the storage path
     * @throws IOException if file storage fails
     */
    public String storeFile(MultipartFile file) throws IOException {
        // Create upload directory if it doesn't exist
        String uploadDir = applicationProperties.getFileStorage().getUploadDir();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFilename = file.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + fileExtension;

        // Store file
        Path targetLocation = uploadPath.resolve(uniqueFilename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        LOG.debug("File stored at: {}", targetLocation.toString());
        return targetLocation.toString();
    }

    /**
     * Delete file from storage.
     *
     * @param storagePath the file storage path
     * @return true if deleted successfully
     */
    public boolean deleteFile(String storagePath) {
        try {
            Path filePath = Paths.get(storagePath);
            return Files.deleteIfExists(filePath);
        } catch (IOException e) {
            LOG.error("Error deleting file: {}", storagePath, e);
            return false;
        }
    }

    /**
     * Get file type from content type and filename.
     *
     * @param contentType the content type
     * @param filename the filename
     * @return the file type
     */
    public FileType getFileType(String contentType, String filename) {
        if (PDF_CONTENT_TYPE.equals(contentType) || (filename != null && filename.toLowerCase().endsWith(".pdf"))) {
            return FileType.PDF;
        } else if (DOCX_CONTENT_TYPE.equals(contentType) || (filename != null && filename.toLowerCase().endsWith(".docx"))) {
            return FileType.DOCX;
        }
        return null;
    }

    private boolean isValidFileType(String contentType, String filename) {
        return getFileType(contentType, filename) != null;
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            return "";
        }
        int lastDotIndex = filename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex);
    }
}
