package com.exceed.app.web.rest;

import com.exceed.app.domain.Word;
import com.exceed.app.repository.WordRepository;
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
 * REST controller for managing {@link com.exceed.app.domain.Word}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class WordResource {

    private final Logger log = LoggerFactory.getLogger(WordResource.class);

    private static final String ENTITY_NAME = "word";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WordRepository wordRepository;

    public WordResource(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    /**
     * {@code POST  /words} : Create a new word.
     *
     * @param word the word to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new word, or with status {@code 400 (Bad Request)} if the word has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/words")
    public ResponseEntity<Word> createWord(@Valid @RequestBody Word word) throws URISyntaxException {
        log.debug("REST request to save Word : {}", word);
        if (word.getId() != null) {
            throw new BadRequestAlertException("A new word cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Word result = wordRepository.save(word);
        return ResponseEntity.created(new URI("/api/words/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /words} : Updates an existing word.
     *
     * @param word the word to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated word,
     * or with status {@code 400 (Bad Request)} if the word is not valid,
     * or with status {@code 500 (Internal Server Error)} if the word couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/words")
    public ResponseEntity<Word> updateWord(@Valid @RequestBody Word word) throws URISyntaxException {
        log.debug("REST request to update Word : {}", word);
        if (word.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Word result = wordRepository.save(word);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, word.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /words} : get all the words.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of words in body.
     */
    @GetMapping("/words")
    public List<Word> getAllWords() {
        log.debug("REST request to get all Words");
        return wordRepository.findAll();
    }

    /**
     * {@code GET  /words/:id} : get the "id" word.
     *
     * @param id the id of the word to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the word, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/words/{id}")
    public ResponseEntity<Word> getWord(@PathVariable Long id) {
        log.debug("REST request to get Word : {}", id);
        Optional<Word> word = wordRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(word);
    }

    /**
     * {@code DELETE  /words/:id} : delete the "id" word.
     *
     * @param id the id of the word to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/words/{id}")
    public ResponseEntity<Void> deleteWord(@PathVariable Long id) {
        log.debug("REST request to delete Word : {}", id);

        wordRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
