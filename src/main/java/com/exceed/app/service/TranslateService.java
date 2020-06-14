package com.exceed.app.service;

import com.exceed.app.service.dto.TranslateDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.exceed.app.domain.Translate}.
 */
public interface TranslateService {
    /**
     * Save a translate.
     *
     * @param translateDTO the entity to save.
     * @return the persisted entity.
     */
    TranslateDTO save(TranslateDTO translateDTO);

    /**
     * Get all the translates.
     *
     * @return the list of entities.
     */
    List<TranslateDTO> findAll();

    /**
     * Get the "id" translate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TranslateDTO> findOne(Long id);

    /**
     * Delete the "id" translate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
