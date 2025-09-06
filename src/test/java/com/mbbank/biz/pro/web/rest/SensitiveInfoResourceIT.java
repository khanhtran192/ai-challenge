package com.mbbank.biz.pro.web.rest;

import static com.mbbank.biz.pro.domain.SensitiveInfoAsserts.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbbank.biz.pro.IntegrationTest;
import com.mbbank.biz.pro.domain.Document;
import com.mbbank.biz.pro.domain.SensitiveInfo;
import com.mbbank.biz.pro.repository.SensitiveInfoRepository;
import com.mbbank.biz.pro.service.mapper.SensitiveInfoMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SensitiveInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SensitiveInfoResourceIT {

    private static final String DEFAULT_INFO_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INFO_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;
    private static final Integer SMALLER_PAGE_NUMBER = 1 - 1;

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final Instant DEFAULT_DETECTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DETECTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/sensitive-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SensitiveInfoRepository sensitiveInfoRepository;

    @Autowired
    private SensitiveInfoMapper sensitiveInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSensitiveInfoMockMvc;

    private SensitiveInfo sensitiveInfo;

    private SensitiveInfo insertedSensitiveInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SensitiveInfo createEntity(EntityManager em) {
        SensitiveInfo sensitiveInfo = new SensitiveInfo()
            .infoType(DEFAULT_INFO_TYPE)
            .content(DEFAULT_CONTENT)
            .pageNumber(DEFAULT_PAGE_NUMBER)
            .position(DEFAULT_POSITION)
            .detectedAt(DEFAULT_DETECTED_AT);
        // Add required entity
        Document document;
        if (TestUtil.findAll(em, Document.class).isEmpty()) {
            document = DocumentResourceIT.createEntity(em);
            em.persist(document);
            em.flush();
        } else {
            document = TestUtil.findAll(em, Document.class).get(0);
        }
        sensitiveInfo.setDocument(document);
        return sensitiveInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SensitiveInfo createUpdatedEntity(EntityManager em) {
        SensitiveInfo updatedSensitiveInfo = new SensitiveInfo()
            .infoType(UPDATED_INFO_TYPE)
            .content(UPDATED_CONTENT)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .position(UPDATED_POSITION)
            .detectedAt(UPDATED_DETECTED_AT);
        // Add required entity
        Document document;
        if (TestUtil.findAll(em, Document.class).isEmpty()) {
            document = DocumentResourceIT.createUpdatedEntity(em);
            em.persist(document);
            em.flush();
        } else {
            document = TestUtil.findAll(em, Document.class).get(0);
        }
        updatedSensitiveInfo.setDocument(document);
        return updatedSensitiveInfo;
    }

    @BeforeEach
    void initTest() {
        sensitiveInfo = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedSensitiveInfo != null) {
            sensitiveInfoRepository.delete(insertedSensitiveInfo);
            insertedSensitiveInfo = null;
        }
    }

    @Test
    @Transactional
    void getAllSensitiveInfos() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList
        restSensitiveInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensitiveInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].infoType").value(hasItem(DEFAULT_INFO_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].detectedAt").value(hasItem(DEFAULT_DETECTED_AT.toString())));
    }

    @Test
    @Transactional
    void getSensitiveInfo() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get the sensitiveInfo
        restSensitiveInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, sensitiveInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sensitiveInfo.getId().intValue()))
            .andExpect(jsonPath("$.infoType").value(DEFAULT_INFO_TYPE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.detectedAt").value(DEFAULT_DETECTED_AT.toString()));
    }

    @Test
    @Transactional
    void getSensitiveInfosByIdFiltering() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        Long id = sensitiveInfo.getId();

        defaultSensitiveInfoFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSensitiveInfoFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSensitiveInfoFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByInfoTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where infoType equals to
        defaultSensitiveInfoFiltering("infoType.equals=" + DEFAULT_INFO_TYPE, "infoType.equals=" + UPDATED_INFO_TYPE);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByInfoTypeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where infoType in
        defaultSensitiveInfoFiltering("infoType.in=" + DEFAULT_INFO_TYPE + "," + UPDATED_INFO_TYPE, "infoType.in=" + UPDATED_INFO_TYPE);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByInfoTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where infoType is not null
        defaultSensitiveInfoFiltering("infoType.specified=true", "infoType.specified=false");
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByInfoTypeContainsSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where infoType contains
        defaultSensitiveInfoFiltering("infoType.contains=" + DEFAULT_INFO_TYPE, "infoType.contains=" + UPDATED_INFO_TYPE);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByInfoTypeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where infoType does not contain
        defaultSensitiveInfoFiltering("infoType.doesNotContain=" + UPDATED_INFO_TYPE, "infoType.doesNotContain=" + DEFAULT_INFO_TYPE);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPageNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where pageNumber equals to
        defaultSensitiveInfoFiltering("pageNumber.equals=" + DEFAULT_PAGE_NUMBER, "pageNumber.equals=" + UPDATED_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPageNumberIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where pageNumber in
        defaultSensitiveInfoFiltering(
            "pageNumber.in=" + DEFAULT_PAGE_NUMBER + "," + UPDATED_PAGE_NUMBER,
            "pageNumber.in=" + UPDATED_PAGE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPageNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where pageNumber is not null
        defaultSensitiveInfoFiltering("pageNumber.specified=true", "pageNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPageNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where pageNumber is greater than or equal to
        defaultSensitiveInfoFiltering(
            "pageNumber.greaterThanOrEqual=" + DEFAULT_PAGE_NUMBER,
            "pageNumber.greaterThanOrEqual=" + UPDATED_PAGE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPageNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where pageNumber is less than or equal to
        defaultSensitiveInfoFiltering(
            "pageNumber.lessThanOrEqual=" + DEFAULT_PAGE_NUMBER,
            "pageNumber.lessThanOrEqual=" + SMALLER_PAGE_NUMBER
        );
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPageNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where pageNumber is less than
        defaultSensitiveInfoFiltering("pageNumber.lessThan=" + UPDATED_PAGE_NUMBER, "pageNumber.lessThan=" + DEFAULT_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPageNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where pageNumber is greater than
        defaultSensitiveInfoFiltering("pageNumber.greaterThan=" + SMALLER_PAGE_NUMBER, "pageNumber.greaterThan=" + DEFAULT_PAGE_NUMBER);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where position equals to
        defaultSensitiveInfoFiltering("position.equals=" + DEFAULT_POSITION, "position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where position in
        defaultSensitiveInfoFiltering("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION, "position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where position is not null
        defaultSensitiveInfoFiltering("position.specified=true", "position.specified=false");
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPositionContainsSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where position contains
        defaultSensitiveInfoFiltering("position.contains=" + DEFAULT_POSITION, "position.contains=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByPositionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where position does not contain
        defaultSensitiveInfoFiltering("position.doesNotContain=" + UPDATED_POSITION, "position.doesNotContain=" + DEFAULT_POSITION);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByDetectedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where detectedAt equals to
        defaultSensitiveInfoFiltering("detectedAt.equals=" + DEFAULT_DETECTED_AT, "detectedAt.equals=" + UPDATED_DETECTED_AT);
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByDetectedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where detectedAt in
        defaultSensitiveInfoFiltering(
            "detectedAt.in=" + DEFAULT_DETECTED_AT + "," + UPDATED_DETECTED_AT,
            "detectedAt.in=" + UPDATED_DETECTED_AT
        );
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByDetectedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSensitiveInfo = sensitiveInfoRepository.saveAndFlush(sensitiveInfo);

        // Get all the sensitiveInfoList where detectedAt is not null
        defaultSensitiveInfoFiltering("detectedAt.specified=true", "detectedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllSensitiveInfosByDocumentIsEqualToSomething() throws Exception {
        Document document;
        if (TestUtil.findAll(em, Document.class).isEmpty()) {
            sensitiveInfoRepository.saveAndFlush(sensitiveInfo);
            document = DocumentResourceIT.createEntity(em);
        } else {
            document = TestUtil.findAll(em, Document.class).get(0);
        }
        em.persist(document);
        em.flush();
        sensitiveInfo.setDocument(document);
        sensitiveInfoRepository.saveAndFlush(sensitiveInfo);
        Long documentId = document.getId();
        // Get all the sensitiveInfoList where document equals to documentId
        defaultSensitiveInfoShouldBeFound("documentId.equals=" + documentId);

        // Get all the sensitiveInfoList where document equals to (documentId + 1)
        defaultSensitiveInfoShouldNotBeFound("documentId.equals=" + (documentId + 1));
    }

    private void defaultSensitiveInfoFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSensitiveInfoShouldBeFound(shouldBeFound);
        defaultSensitiveInfoShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSensitiveInfoShouldBeFound(String filter) throws Exception {
        restSensitiveInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sensitiveInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].infoType").value(hasItem(DEFAULT_INFO_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].detectedAt").value(hasItem(DEFAULT_DETECTED_AT.toString())));

        // Check, that the count call also returns 1
        restSensitiveInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSensitiveInfoShouldNotBeFound(String filter) throws Exception {
        restSensitiveInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSensitiveInfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSensitiveInfo() throws Exception {
        // Get the sensitiveInfo
        restSensitiveInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    protected long getRepositoryCount() {
        return sensitiveInfoRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected SensitiveInfo getPersistedSensitiveInfo(SensitiveInfo sensitiveInfo) {
        return sensitiveInfoRepository.findById(sensitiveInfo.getId()).orElseThrow();
    }

    protected void assertPersistedSensitiveInfoToMatchAllProperties(SensitiveInfo expectedSensitiveInfo) {
        assertSensitiveInfoAllPropertiesEquals(expectedSensitiveInfo, getPersistedSensitiveInfo(expectedSensitiveInfo));
    }

    protected void assertPersistedSensitiveInfoToMatchUpdatableProperties(SensitiveInfo expectedSensitiveInfo) {
        assertSensitiveInfoAllUpdatablePropertiesEquals(expectedSensitiveInfo, getPersistedSensitiveInfo(expectedSensitiveInfo));
    }
}
