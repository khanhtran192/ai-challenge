package com.mbbank.biz.pro.web.rest.errors;

import com.mbbank.biz.pro.service.dto.ApiResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Global exception handler for REST controllers.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle authentication exceptions.
     *
     * @param ex the authentication exception
     * @return error response
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleAuthenticationException(AuthenticationException ex) {
        LOG.warn("Authentication error: {}", ex.getMessage());
        ApiResponseDTO<Void> response = ApiResponseDTO.error("AUTHENTICATION_ERROR", "Lỗi xác thực người dùng");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Handle access denied exceptions.
     *
     * @param ex the access denied exception
     * @return error response
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        LOG.warn("Access denied: {}", ex.getMessage());
        ApiResponseDTO<Void> response = ApiResponseDTO.error("ACCESS_DENIED", "Không có quyền truy cập");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    /**
     * Handle file upload size exceeded exceptions.
     *
     * @param ex the max upload size exceeded exception
     * @return error response
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        LOG.warn("File upload size exceeded: {}", ex.getMessage());
        ApiResponseDTO<Void> response = ApiResponseDTO.error("FILE_TOO_LARGE", "Kích thước file không được vượt quá 50MB");
        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
    }

    /**
     * Handle illegal argument exceptions.
     *
     * @param ex the illegal argument exception
     * @return error response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        LOG.warn("Illegal argument: {}", ex.getMessage());
        ApiResponseDTO<Void> response = ApiResponseDTO.error("INVALID_ARGUMENT", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle general exceptions.
     *
     * @param ex the general exception
     * @return error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleGeneralException(Exception ex) {
        LOG.error("Unexpected error", ex);
        ApiResponseDTO<Void> response = ApiResponseDTO.error("INTERNAL_SERVER_ERROR", "Đã xảy ra lỗi hệ thống");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
