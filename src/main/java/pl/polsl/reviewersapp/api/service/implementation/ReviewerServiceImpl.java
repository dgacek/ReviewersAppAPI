package pl.polsl.reviewersapp.api.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.reviewersapp.api.model.dto.mapper.ReviewerMapper;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerAddDTO;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerGetDTO;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.FacultyEntity;
import pl.polsl.reviewersapp.api.model.entity.ReviewerEntity;
import pl.polsl.reviewersapp.api.model.entity.TagEntity;
import pl.polsl.reviewersapp.api.model.repo.FacultyRepo;
import pl.polsl.reviewersapp.api.model.repo.ReviewerRepo;
import pl.polsl.reviewersapp.api.model.repo.TagRepo;
import pl.polsl.reviewersapp.api.service.ReviewerService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ReviewerServiceImpl implements ReviewerService {
    private ReviewerRepo reviewerRepo;
    private FacultyRepo facultyRepo;
    private TagRepo tagRepo;

    @Override
    public ReviewerGetDTO get(Long id) {
        return ReviewerMapper.INSTANCE.toGetDTO(reviewerRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Reviewer id %d does not exist", id))));
    }

    @Override
    public List<ReviewerGetDTO> getAll() {
        return ReviewerMapper.INSTANCE.toGetDTOList(reviewerRepo.findAll());
    }

    @Override
    public ReviewerGetDTO add(ReviewerAddDTO input) {
        FacultyEntity facultyEntity = facultyRepo.findById(input.getFacultyId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Faculty id %d does not exist", input.getFacultyId())));
        List<TagEntity> tagEntityList = new ArrayList<>();
        for (Long id : input.getTagIdList()) {
            tagEntityList.add(tagRepo.findById(id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("Tag id %d does not exist", id))));
        }
        return ReviewerMapper.INSTANCE.toGetDTO(reviewerRepo.save(ReviewerEntity.builder()
                .name(input.getName())
                .surname(input.getSurname())
                .title(input.getTitle())
                .faculty(facultyEntity)
                .tags(tagEntityList)
                .build()));
    }

    @Override
    @Transactional
    public ReviewerGetDTO update(ReviewerUpdateDTO input) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
