//package com.mbbank.biz.pro.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mbbank.biz.pro.IntegrationTest;
//import com.mbbank.biz.pro.config.TestSecurityConfiguration;
//import com.mbbank.biz.pro.domain.AppUser;
//import com.mbbank.biz.pro.domain.Document;
//import com.mbbank.biz.pro.domain.enumeration.DocumentStatus;
//import com.mbbank.biz.pro.domain.enumeration.FileType;
//import com.mbbank.biz.pro.domain.enumeration.Role;
//import com.mbbank.biz.pro.repository.AppUserRepository;
//import com.mbbank.biz.pro.repository.DocumentRepository;
//import com.mbbank.biz.pro.service.dto.ApiResponseDTO;
//import java.time.Instant;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Integration tests for the {@link DocumentController} REST controller.
// */
//@IntegrationTest
//@AutoConfigureWebMvc
//@WithMockUser
//class DocumentControllerIT {
//
//    private static final String DEFAULT_EMAIL = "test@example.com";
//    private static final String DEFAULT_FULL_NAME = "Test User";
//    private static final String DEFAULT_PASSWORD_HASH = "$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vFnbbqFbkqVE6";
//
//    @Autowired
//    private DocumentRepository documentRepository;
//
//    @Autowired
//    private AppUserRepository appUserRepository;
//
//    @Autowired
//    private MockMvc restDocumentMockMvc;
//
//    @Autowired
//    private ObjectMapper om;
//
//    private AppUser testUser;
//
//    @BeforeEach
//    void initTest() {
//        testUser = createTestUser();
//    }
//
//    private AppUser createTestUser() {
//        AppUser user = new AppUser();
//        user.setEmail(DEFAULT_EMAIL);
//        user.setFullName(DEFAULT_FULL_NAME);
//        user.setPasswordHash(DEFAULT_PASSWORD_HASH);
//        user.setRole(Role.user);
//        user.setCreatedAt(Instant.now());
//        return appUserRepository.saveAndFlush(user);
//    }
//
//    @Test
//    @Transactional
//    @WithMockUser(username = DEFAULT_EMAIL)
//    void uploadDocument_withValidPdfFile_shouldSucceed() throws Exception {
//        // Given
//        MockMultipartFile file = new MockMultipartFile(
//            "file",
//            "test.pdf",
//            "application/pdf",
//            "PDF content".getBytes()
//        );
//
//        // When & Then
//        restDocumentMockMvc
//            .perform(multipart("/api/documents/upload").file(file))
//            .andExpect(status().isCreated())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.success").value(true))
//            .andExpect(jsonPath("$.data.fileName").value("test.pdf"))
//            .andExpect(jsonPath("$.data.fileType").value("PDF"))
//            .andExpect(jsonPath("$.data.status").value("uploaded"))
//            .andExpect(jsonPath("$.message").value("Upload tài liệu thành công. Đang bắt đầu quá trình rà quét."));
//
//        // Verify document was saved
//        assertThat(documentRepository.count()).isEqualTo(1);
//        Document savedDocument = documentRepository.findAll().get(0);
//        assertThat(savedDocument.getFileName()).isEqualTo("test.pdf");
//        assertThat(savedDocument.getFileType()).isEqualTo(FileType.PDF);
//        assertThat(savedDocument.getStatus()).isEqualTo(DocumentStatus.uploaded);
//        assertThat(savedDocument.getOwner().getEmail()).isEqualTo(DEFAULT_EMAIL);
//    }
//
//    @Test
//    @Transactional
//    @WithMockUser(username = DEFAULT_EMAIL)
//    void uploadDocument_withInvalidFileType_shouldFail() throws Exception {
//        // Given
//        MockMultipartFile file = new MockMultipartFile(
//            "file",
//            "test.txt",
//            "text/plain",
//            "Text content".getBytes()
//        );
//
//        // When & Then
//        restDocumentMockMvc
//            .perform(multipart("/api/documents/upload").file(file))
//            .andExpect(status().isBadRequest())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.success").value(false))
//            .andExpect(jsonPath("$.error.code").value("INVALID_FILE_TYPE"))
//            .andExpect(jsonPath("$.error.message").value("Chỉ chấp nhận file PDF hoặc DOCX"));
//
//        // Verify no document was saved
//        assertThat(documentRepository.count()).isEqualTo(0);
//    }
//
//    @Test
//    @Transactional
//    @WithMockUser(username = DEFAULT_EMAIL)
//    void getMyDocuments_withNoDocuments_shouldReturnEmpty() throws Exception {
//        // When & Then
//        restDocumentMockMvc
//            .perform(get("/api/documents/my-documents"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.success").value(true))
//            .andExpect(jsonPath("$.data.documents").isEmpty())
//            .andExpect(jsonPath("$.data.pagination.totalElements").value(0));
//    }
//
//    @Test
//    @Transactional
//    @WithMockUser(username = DEFAULT_EMAIL)
//    void getMyDocuments_withExistingDocuments_shouldReturnDocuments() throws Exception {
//        // Given
//        Document document = new Document();
//        document.setFileName("test.pdf");
//        document.setFileType(FileType.PDF);
//        document.setFileSize(1024L);
//        document.setStatus(DocumentStatus.ready);
//        document.setStoragePath("/tmp/test.pdf");
//        document.setUploadedAt(Instant.now());
//        document.setOwner(testUser);
//        documentRepository.saveAndFlush(document);
//
//        // When & Then
//        restDocumentMockMvc
//            .perform(get("/api/documents/my-documents"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.success").value(true))
//            .andExpect(jsonPath("$.data.documents").isArray())
//            .andExpect(jsonPath("$.data.documents[0].fileName").value("test.pdf"))
//            .andExpect(jsonPath("$.data.documents[0].fileType").value("PDF"))
//            .andExpect(jsonPath("$.data.documents[0].status").value("ready"))
//            .andExpect(jsonPath("$.data.pagination.totalElements").value(1));
//    }
//
//    @Test
//    @Transactional
//    void uploadDocument_withoutAuthentication_shouldFail() throws Exception {
//        // Given
//        MockMultipartFile file = new MockMultipartFile(
//            "file",
//            "test.pdf",
//            "application/pdf",
//            "PDF content".getBytes()
//        );
//
//        // When & Then
//        restDocumentMockMvc
//            .perform(multipart("/api/documents/upload").file(file))
//            .andExpect(status().isUnauthorized());
//    }
//}
