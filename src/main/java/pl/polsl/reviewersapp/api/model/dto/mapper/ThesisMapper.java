package pl.polsl.reviewersapp.api.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisGetDTO;
import pl.polsl.reviewersapp.api.model.entity.ThesisEntity;

import java.util.List;

@Mapper
public interface ThesisMapper {
    ThesisMapper INSTANCE = Mappers.getMapper(ThesisMapper.class);

    ThesisGetDTO toGetUpdateDTO(ThesisEntity thesisEntity);
    List<ThesisGetDTO> toGetUpdateDTOList(List<ThesisEntity> thesisEntityList);
}
