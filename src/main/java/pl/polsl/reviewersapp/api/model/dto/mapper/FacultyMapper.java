package pl.polsl.reviewersapp.api.model.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.polsl.reviewersapp.api.model.dto.faculty.FacultyGetUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.FacultyEntity;

import java.util.List;

@Mapper
public interface FacultyMapper {
    FacultyMapper INSTANCE = Mappers.getMapper(FacultyMapper.class);

    FacultyGetUpdateDTO toGetUpdateDTO(FacultyEntity facultyEntity);
    List<FacultyGetUpdateDTO> toGetUpdateDTOList(List<FacultyEntity> facultyEntityList);
}
