package com.mbbank.biz.pro.web.rest;

import static com.mbbank.biz.pro.domain.DocumentShareAsserts.*;
import static com.mbbank.biz.pro.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbbank.biz.pro.IntegrationTest;
import com.mbbank.biz.pro.domain.AppUser;
import com.mbbank.biz.pro.domain.Document;
import com.mbbank.biz.pro.domain.DocumentShare;
import com.mbbank.biz.pro.domain.enumeration.SharePermission;
import com.mbbank.biz.pro.repository.DocumentShareRepository;
import com.mbbank.biz.pro.service.dto.DocumentShareDTO;
import com.mbbank.biz.pro.service.mapper.DocumentShareMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
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
 * Integration tests for the {@link DocumentShareResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumentShareResourceIT {

    private static final SharePermission DEFAULT_PERMISSION = SharePermission.view;
    private static final SharePermission UPDATED_PERMISSION = SharePermission.view;

    private static final Instant DEFAULT_GRANTED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GRANTED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/document-shares";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DocumentShareRepository documentShareRepository;

    @Autowired
    private DocumentShareMapper documentShareMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumentShareMockMvc;

    private DocumentShare documentShare;

    private DocumentShare insertedDocumentShare;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentShare createEntity(EntityManager em) {
        DocumentShare documentShare = new DocumentShare().permission(DEFAULT_PERMISSION).grantedAt(DEFAULT_GRANTED_AT);
        // Add required entity
        Document document;
        if (TestUtil.findAll(em, Document.class).isEmpty()) {
            document = DocumentResourceIT.createEntity(em);
            em.persist(document);
            em.flush();
        } else {
            document = TestUtil.findAll(em, Document.class).get(0);
        }
        documentShare.setDocument(document);
        // Add required entity
        AppUser appUser;
        if (TestUtil.findAll(em, AppUser.class).isEmpty()) {
            appUser = AppUserResourceIT.createEntity();
            em.persist(appUser);
            em.flush();
        } else {
            appUser = TestUtil.findAll(em, AppUser.class).get(0);
        }
        documentShare.setUser(appUser);
        return documentShare;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentShare createUpdatedEntity(EntityManager em) {
        DocumentShare updatedDocumentShare = new DocumentShare().permission(UPDATED_PERMISSION).grantedAt(UPDATED_GRANTED_AT);
        // Add required entity
        Document document;
        if (TestUtil.findAll(em, Document.class).isEmpty()) {
            document = DocumentResourceIT.createUpdatedEntity(em);
            em.persist(document);
            em.flush();
        } else {
            document = TestUtil.findAll(em, Document.class).get(0);
        }
        updatedDocumentShare.setDocument(document);
        // Add required entity
        AppUser appUser;
        if (TestUtil.findAll(em, AppUser.class).isEmpty()) {
            appUser = AppUserResourceIT.createUpdatedEntity();
            em.persist(appUser);
            em.flush();
        } else {
            appUser = TestUtil.findAll(em, AppUser.class).get(0);
        }
        updatedDocumentShare.setUser(appUser);
        return updatedDocumentShare;
    }

    @BeforeEach
    void initTest() {
        documentShare = createEntity(em);
    }

    @AfterEach
    void cleanup() {
        if (insertedDocumentShare != null) {
            documentShareRepository.delete(insertedDocumentShare);
            insertedDocumentShare = null;
        }
    }

    @Test
    @Transactional
    void createDocumentShare() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DocumentShare
        DocumentShareDTO documentShareDTO = documentShareMapper.toDto(documentShare);
        var returnedDocumentShareDTO = om.readValue(
            restDocumentShareMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentShareDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DocumentShareDTO.class
        );

        // Validate the DocumentShare in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDocumentShare = documentShareMapper.toEntity(returnedDocumentShareDTO);
        assertDocumentShareUpdatableFieldsEquals(returnedDocumentShare, getPersistedDocumentShare(returnedDocumentShare));

        insertedDocumentShare = returnedDocumentShare;
    }

    @Test
    @Transactional
    void createDocumentShareWithExistingId() throws Exception {
        // Create the DocumentShare with an existing ID
        documentShare.setId(1L);
        DocumentShareDTO documentShareDTO = documentShareMapper.toDto(documentShare);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentShareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentShareDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentShare in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPermissionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        documentShare.setPermission(null);

        // Create the DocumentShare, which fails.
        DocumentShareDTO documentShareDTO = documentShareMapper.toDto(documentShare);

        restDocumentShareMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentShareDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocumentShares() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        // Get all the documentShareList
        restDocumentShareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentShare.getId().intValue())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())))
            .andExpect(jsonPath("$.[*].grantedAt").value(hasItem(DEFAULT_GRANTED_AT.toString())));
    }

    @Test
    @Transactional
    void getDocumentShare() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        // Get the documentShare
        restDocumentShareMockMvc
            .perform(get(ENTITY_API_URL_ID, documentShare.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documentShare.getId().intValue()))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION.toString()))
            .andExpect(jsonPath("$.grantedAt").value(DEFAULT_GRANTED_AT.toString()));
    }

    @Test
    @Transactional
    void getDocumentSharesByIdFiltering() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        Long id = documentShare.getId();

        defaultDocumentShareFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultDocumentShareFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultDocumentShareFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDocumentSharesByPermissionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        // Get all the documentShareList where permission equals to
        defaultDocumentShareFiltering("permission.equals=" + DEFAULT_PERMISSION, "permission.equals=" + UPDATED_PERMISSION);
    }

    @Test
    @Transactional
    void getAllDocumentSharesByPermissionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        // Get all the documentShareList where permission in
        defaultDocumentShareFiltering(
            "permission.in=" + DEFAULT_PERMISSION + "," + UPDATED_PERMISSION,
            "permission.in=" + UPDATED_PERMISSION
        );
    }

    @Test
    @Transactional
    void getAllDocumentSharesByPermissionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        // Get all the documentShareList where permission is not null
        defaultDocumentShareFiltering("permission.specified=true", "permission.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentSharesByGrantedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        // Get all the documentShareList where grantedAt equals to
        defaultDocumentShareFiltering("grantedAt.equals=" + DEFAULT_GRANTED_AT, "grantedAt.equals=" + UPDATED_GRANTED_AT);
    }

    @Test
    @Transactional
    void getAllDocumentSharesByGrantedAtIsInShouldWork() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        // Get all the documentShareList where grantedAt in
        defaultDocumentShareFiltering(
            "grantedAt.in=" + DEFAULT_GRANTED_AT + "," + UPDATED_GRANTED_AT,
            "grantedAt.in=" + UPDATED_GRANTED_AT
        );
    }

    @Test
    @Transactional
    void getAllDocumentSharesByGrantedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        // Get all the documentShareList where grantedAt is not null
        defaultDocumentShareFiltering("grantedAt.specified=true", "grantedAt.specified=false");
    }

    @Test
    @Transactional
    void getAllDocumentSharesByDocumentIsEqualToSomething() throws Exception {
        Document document;
        if (TestUtil.findAll(em, Document.class).isEmpty()) {
            documentShareRepository.saveAndFlush(documentShare);
            document = DocumentResourceIT.createEntity(em);
        } else {
            document = TestUtil.findAll(em, Document.class).get(0);
        }
        em.persist(document);
        em.flush();
        documentShare.setDocument(document);
        documentShareRepository.saveAndFlush(documentShare);
        Long documentId = document.getId();
        // Get all the documentShareList where document equals to documentId
        defaultDocumentShareShouldBeFound("documentId.equals=" + documentId);

        // Get all the documentShareList where document equals to (documentId + 1)
        defaultDocumentShareShouldNotBeFound("documentId.equals=" + (documentId + 1));
    }

    @Test
    @Transactional
    void getAllDocumentSharesByUserIsEqualToSomething() throws Exception {
        AppUser user;
        if (TestUtil.findAll(em, AppUser.class).isEmpty()) {
            documentShareRepository.saveAndFlush(documentShare);
            user = AppUserResourceIT.createEntity();
        } else {
            user = TestUtil.findAll(em, AppUser.class).get(0);
        }
        em.persist(user);
        em.flush();
        documentShare.setUser(user);
        documentShareRepository.saveAndFlush(documentShare);
        Long userId = user.getId();
        // Get all the documentShareList where user equals to userId
        defaultDocumentShareShouldBeFound("userId.equals=" + userId);

        // Get all the documentShareList where user equals to (userId + 1)
        defaultDocumentShareShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    private void defaultDocumentShareFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultDocumentShareShouldBeFound(shouldBeFound);
        defaultDocumentShareShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDocumentShareShouldBeFound(String filter) throws Exception {
        restDocumentShareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentShare.getId().intValue())))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION.toString())))
            .andExpect(jsonPath("$.[*].grantedAt").value(hasItem(DEFAULT_GRANTED_AT.toString())));

        // Check, that the count call also returns 1
        restDocumentShareMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDocumentShareShouldNotBeFound(String filter) throws Exception {
        restDocumentShareMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDocumentShareMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDocumentShare() throws Exception {
        // Get the documentShare
        restDocumentShareMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDocumentShare() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentShare
        DocumentShare updatedDocumentShare = documentShareRepository.findById(documentShare.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDocumentShare are not directly saved in db
        em.detach(updatedDocumentShare);
        updatedDocumentShare.permission(UPDATED_PERMISSION).grantedAt(UPDATED_GRANTED_AT);
        DocumentShareDTO documentShareDTO = documentShareMapper.toDto(updatedDocumentShare);

        restDocumentShareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentShareDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentShareDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocumentShare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDocumentShareToMatchAllProperties(updatedDocumentShare);
    }

    @Test
    @Transactional
    void putNonExistingDocumentShare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentShare.setId(longCount.incrementAndGet());

        // Create the DocumentShare
        DocumentShareDTO documentShareDTO = documentShareMapper.toDto(documentShare);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentShareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documentShareDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentShareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentShare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumentShare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentShare.setId(longCount.incrementAndGet());

        // Create the DocumentShare
        DocumentShareDTO documentShareDTO = documentShareMapper.toDto(documentShare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentShareMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(documentShareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentShare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumentShare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentShare.setId(longCount.incrementAndGet());

        // Create the DocumentShare
        DocumentShareDTO documentShareDTO = documentShareMapper.toDto(documentShare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentShareMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(documentShareDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentShare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumentShareWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentShare using partial update
        DocumentShare partialUpdatedDocumentShare = new DocumentShare();
        partialUpdatedDocumentShare.setId(documentShare.getId());

        partialUpdatedDocumentShare.permission(UPDATED_PERMISSION).grantedAt(UPDATED_GRANTED_AT);

        restDocumentShareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentShare.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentShare))
            )
            .andExpect(status().isOk());

        // Validate the DocumentShare in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentShareUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDocumentShare, documentShare),
            getPersistedDocumentShare(documentShare)
        );
    }

    @Test
    @Transactional
    void fullUpdateDocumentShareWithPatch() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the documentShare using partial update
        DocumentShare partialUpdatedDocumentShare = new DocumentShare();
        partialUpdatedDocumentShare.setId(documentShare.getId());

        partialUpdatedDocumentShare.permission(UPDATED_PERMISSION).grantedAt(UPDATED_GRANTED_AT);

        restDocumentShareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumentShare.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDocumentShare))
            )
            .andExpect(status().isOk());

        // Validate the DocumentShare in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDocumentShareUpdatableFieldsEquals(partialUpdatedDocumentShare, getPersistedDocumentShare(partialUpdatedDocumentShare));
    }

    @Test
    @Transactional
    void patchNonExistingDocumentShare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentShare.setId(longCount.incrementAndGet());

        // Create the DocumentShare
        DocumentShareDTO documentShareDTO = documentShareMapper.toDto(documentShare);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentShareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documentShareDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentShareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentShare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumentShare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentShare.setId(longCount.incrementAndGet());

        // Create the DocumentShare
        DocumentShareDTO documentShareDTO = documentShareMapper.toDto(documentShare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentShareMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(documentShareDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumentShare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumentShare() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        documentShare.setId(longCount.incrementAndGet());

        // Create the DocumentShare
        DocumentShareDTO documentShareDTO = documentShareMapper.toDto(documentShare);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumentShareMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(documentShareDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumentShare in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumentShare() throws Exception {
        // Initialize the database
        insertedDocumentShare = documentShareRepository.saveAndFlush(documentShare);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the documentShare
        restDocumentShareMockMvc
            .perform(delete(ENTITY_API_URL_ID, documentShare.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return documentShareRepository.count();
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

    protected DocumentShare getPersistedDocumentShare(DocumentShare documentShare) {
        return documentShareRepository.findById(documentShare.getId()).orElseThrow();
    }

    protected void assertPersistedDocumentShareToMatchAllProperties(DocumentShare expectedDocumentShare) {
        assertDocumentShareAllPropertiesEquals(expectedDocumentShare, getPersistedDocumentShare(expectedDocumentShare));
    }

    protected void assertPersistedDocumentShareToMatchUpdatableProperties(DocumentShare expectedDocumentShare) {
        assertDocumentShareAllUpdatablePropertiesEquals(expectedDocumentShare, getPersistedDocumentShare(expectedDocumentShare));
    }
}
