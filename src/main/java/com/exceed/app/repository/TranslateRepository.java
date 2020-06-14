package com.exceed.app.repository;

import com.exceed.app.domain.Translate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Translate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranslateRepository extends JpaRepository<Translate, Long> {}
