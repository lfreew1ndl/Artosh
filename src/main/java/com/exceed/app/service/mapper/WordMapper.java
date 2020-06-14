package com.exceed.app.service.mapper;

import com.exceed.app.domain.*;
import com.exceed.app.service.dto.WordDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Word} and its DTO {@link WordDTO}.
 */
@Mapper(componentModel = "spring", uses = { CategoryMapper.class })
public interface WordMapper extends EntityMapper<WordDTO, Word> {
    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    WordDTO toDto(Word word);

    @Mapping(source = "categoryId", target = "category")
    Word toEntity(WordDTO wordDTO);

    default Word fromId(Long id) {
        if (id == null) {
            return null;
        }
        Word word = new Word();
        word.setId(id);
        return word;
    }
}
