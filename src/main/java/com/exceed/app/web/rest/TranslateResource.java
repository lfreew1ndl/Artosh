package com.exceed.app.web.rest;

import com.exceed.app.service.TranslateService;
import com.exceed.app.service.dto.TranslateDTO;
import com.exceed.app.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.exceed.app.domain.Translate}.
 */
@RestController
@RequestMapping("/api")
public class TranslateResource {
    private final Logger log = LoggerFactory.getLogger(TranslateResource.class);

    private static final String ENTITY_NAME = "translate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TranslateService translateService;

    public TranslateResource(TranslateService translateService) {
        this.translateService = translateService;
    }

    /**
     * {@code POST  /translates} : Create a new translate.
     *
     * @param translateDTO the translateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new translateDTO, or with status {@code 400 (Bad Request)} if the translate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/translates")
    public ResponseEntity<TranslateDTO> createTranslate(@Valid @RequestBody TranslateDTO translateDTO) throws URISyntaxException {
        log.debug("REST request to save Translate : {}", translateDTO);
        if (translateDTO.getId() != null) {
            throw new BadRequestAlertException("A new translate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TranslateDTO result = translateService.save(translateDTO);
        return ResponseEntity
            .created(new URI("/api/translates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /translates} : Updates an existing translate.
     *
     * @param translateDTO the translateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated translateDTO,
     * or with status {@code 400 (Bad Request)} if the translateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the translateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/translates")
    public ResponseEntity<TranslateDTO> updateTranslate(@Valid @RequestBody TranslateDTO translateDTO) throws URISyntaxException {
        log.debug("REST request to update Translate : {}", translateDTO);
        if (translateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TranslateDTO result = translateService.save(translateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, translateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /translates} : get all the translates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of translates in body.
     */
    @GetMapping("/translates")
    public List<TranslateDTO> getAllTranslates() {
        log.debug("REST request to get all Translates");
        return translateService.findAll();
    }

    /**
     * {@code GET  /translates/:id} : get the "id" translate.
     *
     * @param id the id of the translateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the translateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/translates/{id}")
    public ResponseEntity<TranslateDTO> getTranslate(@PathVariable Long id) {
        log.debug("REST request to get Translate : {}", id);
        Optional<TranslateDTO> translateDTO = translateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(translateDTO);
    }

    /**
     * {@code DELETE  /translates/:id} : delete the "id" translate.
     *
     * @param id the id of the translateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/translates/{id}")
    public ResponseEntity<Void> deleteTranslate(@PathVariable Long id) {
        log.debug("REST request to delete Translate : {}", id);

        translateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
