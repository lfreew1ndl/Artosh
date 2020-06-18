package com.exceed.app.service.impl;

import com.exceed.app.domain.Translate;
import com.exceed.app.repository.TranslateRepository;
import com.exceed.app.service.TranslateService;
import com.exceed.app.service.dto.TranslateDTO;
import com.exceed.app.service.mapper.TranslateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Translate}.
 */
@Service
@Transactional
public class TranslateServiceImpl implements TranslateService {
    private final Logger log = LoggerFactory.getLogger(TranslateServiceImpl.class);

    private final TranslateRepository translateRepository;

    private final TranslateMapper translateMapper;

    public TranslateServiceImpl(TranslateRepository translateRepository, TranslateMapper translateMapper) {
        this.translateRepository = translateRepository;
        this.translateMapper = translateMapper;
    }

    /**
     * Save a translate.
     *
     * @param translateDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TranslateDTO save(TranslateDTO translateDTO) {
        log.debug("Request to save Translate : {}", translateDTO);
        Translate translate = translateMapper.toEntity(translateDTO);
        translate = translateRepository.save(translate);
        return translateMapper.toDto(translate);
    }

    /**
     * Get all the translates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TranslateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Translates");
        return translateRepository.findAll(pageable).map(translateMapper::toDto);
    }

    /**
     * Get one translate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TranslateDTO> findOne(Long id) {
        log.debug("Request to get Translate : {}", id);
        return translateRepository.findById(id).map(translateMapper::toDto);
    }

    /**
     * Delete the translate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Translate : {}", id);

        translateRepository.deleteById(id);
    }
}
