package pl.polsl.reviewersapp.api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.polsl.reviewersapp.api.model.dto.dictionary.DictionaryGetUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.DictionaryEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DictionaryMapper {
    DictionaryMapper INSTANCE = Mappers.getMapper(DictionaryMapper.class);

    DictionaryGetUpdateDTO toGetUpdateDTO(DictionaryEntity dictionaryEntity);
    List<DictionaryGetUpdateDTO> toGetUpdateDTOList(List<DictionaryEntity> dictionaryEntityList);
}
