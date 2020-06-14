package com.exceed.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.exceed.app.ArtoshApp;
import com.exceed.app.domain.Language;
import com.exceed.app.domain.Translate;
import com.exceed.app.domain.Word;
import com.exceed.app.repository.TranslateRepository;
import com.exceed.app.service.TranslateService;
import com.exceed.app.service.dto.TranslateDTO;
import com.exceed.app.service.mapper.TranslateMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TranslateResource} REST controller.
 */
@SpringBootTest(classes = ArtoshApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TranslateResourceIT {
    private static final String DEFAULT_TRANSLATE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATE = "BBBBBBBBBB";

    @Autowired
    private TranslateRepository translateRepository;

    @Autowired
    private TranslateMapper translateMapper;

    @Autowired
    private TranslateService translateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTranslateMockMvc;

    private Translate translate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Translate createEntity(EntityManager em) {
        Translate translate = new Translate().translate(DEFAULT_TRANSLATE);
        // Add required entity
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            language = LanguageResourceIT.createEntity(em);
            em.persist(language);
            em.flush();
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        translate.setLanguage(language);
        // Add required entity
        Word word;
        if (TestUtil.findAll(em, Word.class).isEmpty()) {
            word = WordResourceIT.createEntity(em);
            em.persist(word);
            em.flush();
        } else {
            word = TestUtil.findAll(em, Word.class).get(0);
        }
        translate.setWord(word);
        return translate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Translate createUpdatedEntity(EntityManager em) {
        Translate translate = new Translate().translate(UPDATED_TRANSLATE);
        // Add required entity
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            language = LanguageResourceIT.createUpdatedEntity(em);
            em.persist(language);
            em.flush();
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        translate.setLanguage(language);
        // Add required entity
        Word word;
        if (TestUtil.findAll(em, Word.class).isEmpty()) {
            word = WordResourceIT.createUpdatedEntity(em);
            em.persist(word);
            em.flush();
        } else {
            word = TestUtil.findAll(em, Word.class).get(0);
        }
        translate.setWord(word);
        return translate;
    }

    @BeforeEach
    public void initTest() {
        translate = createEntity(em);
    }

    @Test
    @Transactional
    public void createTranslate() throws Exception {
        int databaseSizeBeforeCreate = translateRepository.findAll().size();
        // Create the Translate
        TranslateDTO translateDTO = translateMapper.toDto(translate);
        restTranslateMockMvc
            .perform(
                post("/api/translates")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(translateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Translate in the database
        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeCreate + 1);
        Translate testTranslate = translateList.get(translateList.size() - 1);
        assertThat(testTranslate.getTranslate()).isEqualTo(DEFAULT_TRANSLATE);
    }

    @Test
    @Transactional
    public void createTranslateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = translateRepository.findAll().size();

        // Create the Translate with an existing ID
        translate.setId(1L);
        TranslateDTO translateDTO = translateMapper.toDto(translate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranslateMockMvc
            .perform(
                post("/api/translates")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(translateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Translate in the database
        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTranslateIsRequired() throws Exception {
        int databaseSizeBeforeTest = translateRepository.findAll().size();
        // set the field null
        translate.setTranslate(null);

        // Create the Translate, which fails.
        TranslateDTO translateDTO = translateMapper.toDto(translate);

        restTranslateMockMvc
            .perform(
                post("/api/translates")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(translateDTO))
            )
            .andExpect(status().isBadRequest());

        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTranslates() throws Exception {
        // Initialize the database
        translateRepository.saveAndFlush(translate);

        // Get all the translateList
        restTranslateMockMvc
            .perform(get("/api/translates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(translate.getId().intValue())))
            .andExpect(jsonPath("$.[*].translate").value(hasItem(DEFAULT_TRANSLATE)));
    }

    @Test
    @Transactional
    public void getTranslate() throws Exception {
        // Initialize the database
        translateRepository.saveAndFlush(translate);

        // Get the translate
        restTranslateMockMvc
            .perform(get("/api/translates/{id}", translate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(translate.getId().intValue()))
            .andExpect(jsonPath("$.translate").value(DEFAULT_TRANSLATE));
    }

    @Test
    @Transactional
    public void getNonExistingTranslate() throws Exception {
        // Get the translate
        restTranslateMockMvc.perform(get("/api/translates/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTranslate() throws Exception {
        // Initialize the database
        translateRepository.saveAndFlush(translate);

        int databaseSizeBeforeUpdate = translateRepository.findAll().size();

        // Update the translate
        Translate updatedTranslate = translateRepository.findById(translate.getId()).get();
        // Disconnect from session so that the updates on updatedTranslate are not directly saved in db
        em.detach(updatedTranslate);
        updatedTranslate.translate(UPDATED_TRANSLATE);
        TranslateDTO translateDTO = translateMapper.toDto(updatedTranslate);

        restTranslateMockMvc
            .perform(
                put("/api/translates")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(translateDTO))
            )
            .andExpect(status().isOk());

        // Validate the Translate in the database
        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeUpdate);
        Translate testTranslate = translateList.get(translateList.size() - 1);
        assertThat(testTranslate.getTranslate()).isEqualTo(UPDATED_TRANSLATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTranslate() throws Exception {
        int databaseSizeBeforeUpdate = translateRepository.findAll().size();

        // Create the Translate
        TranslateDTO translateDTO = translateMapper.toDto(translate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranslateMockMvc
            .perform(
                put("/api/translates")
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(translateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Translate in the database
        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTranslate() throws Exception {
        // Initialize the database
        translateRepository.saveAndFlush(translate);

        int databaseSizeBeforeDelete = translateRepository.findAll().size();

        // Delete the translate
        restTranslateMockMvc
            .perform(delete("/api/translates/{id}", translate.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Translate> translateList = translateRepository.findAll();
        assertThat(translateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
