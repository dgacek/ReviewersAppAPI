package pl.polsl.reviewersapp.api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.polsl.reviewersapp.api.model.dto.user.UserGetDTO;
import pl.polsl.reviewersapp.api.model.entity.UserEntity;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserGetDTO toGetDTO(UserEntity userEntity);
    List<UserGetDTO> toGetDTOList(List<UserEntity> userEntityList);
}
