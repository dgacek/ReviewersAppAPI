package pl.polsl.reviewersapp.api.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerGetDTO;
import pl.polsl.reviewersapp.api.model.entity.ReviewerEntity;

import java.util.List;

@Mapper
public interface ReviewerMapper {
    ReviewerMapper INSTANCE = Mappers.getMapper(ReviewerMapper.class);

    ReviewerGetDTO toGetDTO(ReviewerEntity reviewerEntity);
    List<ReviewerGetDTO> toGetDTOList(List<ReviewerEntity> reviewerEntity);
}
