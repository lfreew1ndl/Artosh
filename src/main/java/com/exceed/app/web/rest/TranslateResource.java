package com.exceed.app.web.rest;

import com.exceed.app.domain.Translate;
import com.exceed.app.repository.TranslateRepository;
import com.exceed.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.exceed.app.domain.Translate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TranslateResource {

    private final Logger log = LoggerFactory.getLogger(TranslateResource.class);

    private static final String ENTITY_NAME = "translate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TranslateRepository translateRepository;

    public TranslateResource(TranslateRepository translateRepository) {
        this.translateRepository = translateRepository;
    }

    /**
     * {@code POST  /translates} : Create a new translate.
     *
     * @param translate the translate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new translate, or with status {@code 400 (Bad Request)} if the translate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/translates")
    public ResponseEntity<Translate> createTranslate(@Valid @RequestBody Translate translate) throws URISyntaxException {
        log.debug("REST request to save Translate : {}", translate);
        if (translate.getId() != null) {
            throw new BadRequestAlertException("A new translate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Translate result = translateRepository.save(translate);
        return ResponseEntity.created(new URI("/api/translates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /translates} : Updates an existing translate.
     *
     * @param translate the translate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated translate,
     * or with status {@code 400 (Bad Request)} if the translate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the translate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/translates")
    public ResponseEntity<Translate> updateTranslate(@Valid @RequestBody Translate translate) throws URISyntaxException {
        log.debug("REST request to update Translate : {}", translate);
        if (translate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Translate result = translateRepository.save(translate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, translate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /translates} : get all the translates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of translates in body.
     */
    @GetMapping("/translates")
    public List<Translate> getAllTranslates() {
        log.debug("REST request to get all Translates");
        return translateRepository.findAll();
    }

    /**
     * {@code GET  /translates/:id} : get the "id" translate.
     *
     * @param id the id of the translate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the translate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/translates/{id}")
    public ResponseEntity<Translate> getTranslate(@PathVariable Long id) {
        log.debug("REST request to get Translate : {}", id);
        Optional<Translate> translate = translateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(translate);
    }

    /**
     * {@code DELETE  /translates/:id} : delete the "id" translate.
     *
     * @param id the id of the translate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/translates/{id}")
    public ResponseEntity<Void> deleteTranslate(@PathVariable Long id) {
        log.debug("REST request to delete Translate : {}", id);

        translateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
