package com.exceed.app.service.mapper;

import com.exceed.app.domain.*;
import com.exceed.app.service.dto.TranslateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Translate} and its DTO {@link TranslateDTO}.
 */
@Mapper(componentModel = "spring", uses = { LanguageMapper.class, WordMapper.class })
public interface TranslateMapper extends EntityMapper<TranslateDTO, Translate> {
    @Mapping(source = "language.id", target = "languageId")
    @Mapping(source = "language.name", target = "languageName")
    @Mapping(source = "word.id", target = "wordId")
    @Mapping(source = "word.word", target = "wordWord")
    TranslateDTO toDto(Translate translate);

    @Mapping(source = "languageId", target = "language")
    @Mapping(source = "wordId", target = "word")
    Translate toEntity(TranslateDTO translateDTO);

    default Translate fromId(Long id) {
        if (id == null) {
            return null;
        }
        Translate translate = new Translate();
        translate.setId(id);
        return translate;
    }
}
