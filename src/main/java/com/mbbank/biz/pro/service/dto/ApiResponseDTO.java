package com.mbbank.biz.pro.service.dto;

import java.io.Serializable;

/**
 * Generic API response wrapper DTO.
 */
public class ApiResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;
    private T data;
    private String message;
    private ErrorDTO error;

    public ApiResponseDTO() {
        // Empty constructor needed for Jackson.
    }

    public ApiResponseDTO(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public ApiResponseDTO(boolean success, ErrorDTO error) {
        this.success = success;
        this.error = error;
    }

    public static <T> ApiResponseDTO<T> success(T data, String message) {
        return new ApiResponseDTO<>(true, data, message);
    }

    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<>(true, data, null);
    }

    public static <T> ApiResponseDTO<T> error(ErrorDTO error) {
        return new ApiResponseDTO<>(false, error);
    }

    public static <T> ApiResponseDTO<T> error(String code, String message) {
        return new ApiResponseDTO<>(false, new ErrorDTO(code, message));
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorDTO getError() {
        return error;
    }

    public void setError(ErrorDTO error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ApiResponseDTO{" + "success=" + success + ", data=" + data + ", message='" + message + "'" + ", error=" + error + "}";
    }

    /**
     * Error DTO for API responses.
     */
    public static class ErrorDTO implements Serializable {

        private static final long serialVersionUID = 1L;

        private String code;
        private String message;

        public ErrorDTO() {
            // Empty constructor needed for Jackson.
        }

        public ErrorDTO(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "ErrorDTO{" + "code='" + code + "'" + ", message='" + message + "'" + "}";
        }
    }
}
