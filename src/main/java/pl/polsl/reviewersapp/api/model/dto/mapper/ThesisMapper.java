package pl.polsl.reviewersapp.api.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisGetUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.ThesisEntity;

@Mapper
public interface ThesisMapper {
    ThesisMapper INSTANCE = Mappers.getMapper(ThesisMapper.class);

    ThesisGetUpdateDTO toGetUpdateDTO(ThesisEntity thesisEntity);
}
