package pl.polsl.reviewersapp.api.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.polsl.reviewersapp.api.model.dto.tag.TagGetUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.TagEntity;

import java.util.List;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagGetUpdateDTO toGetUpdateDTO(TagEntity tagEntity);
    List<TagGetUpdateDTO> toGetUpdateDTOList(List<TagEntity> tagEntityList);
}
