package pl.polsl.reviewersapp.api.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.reviewersapp.api.model.entity.dictionary.TagEntity;
import pl.polsl.reviewersapp.api.model.entity.dictionary.TitleEntity;
import pl.polsl.reviewersapp.api.model.mapper.ReviewerMapper;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerAddDTO;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerGetDTO;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.FacultyEntity;
import pl.polsl.reviewersapp.api.model.entity.ReviewerEntity;
import pl.polsl.reviewersapp.api.model.repo.DictionaryRepo;
import pl.polsl.reviewersapp.api.model.repo.FacultyRepo;
import pl.polsl.reviewersapp.api.model.repo.ReviewerRepo;
import pl.polsl.reviewersapp.api.service.ReviewerService;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ReviewerServiceImpl implements ReviewerService {
    private final ReviewerRepo reviewerRepo;
    private final FacultyRepo facultyRepo;
    private final DictionaryRepo dictionaryRepo;

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
        FacultyEntity facultyEntity = facultyRepo.findById(input.facultyId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Faculty id %d does not exist", input.facultyId())));

        TitleEntity titleEntity = (TitleEntity) dictionaryRepo.findByTypeAndId("title", input.titleId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Title id %d does not exist", input.titleId())));

        List<TagEntity> tagEntityList = new ArrayList<>();
        for (Long id : input.tagIdList()) {
            tagEntityList.add((TagEntity) dictionaryRepo.findByTypeAndId("tag", id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("Tag id %d does not exist", id))));
        }

        return ReviewerMapper.INSTANCE.toGetDTO(reviewerRepo.save(ReviewerEntity.builder()
                .name(input.name())
                .surname(input.surname())
                .faculty(facultyEntity)
                .title(titleEntity)
                .tags(tagEntityList)
                .build()));
    }

    @Override
    @Transactional
    public ReviewerGetDTO update(ReviewerUpdateDTO input) {
        ReviewerEntity reviewerEntity = reviewerRepo.findById(input.id())
                .orElseThrow(() -> new NoSuchElementException(String.format("Reviewer id %d does not exist", input.facultyId())));

        if (input.tagIdList() != null) {
            List<TagEntity> tagEntityList = new ArrayList<>();
            for (Long id : input.tagIdList()) {
                tagEntityList.add((TagEntity) dictionaryRepo.findByTypeAndId("tag", id)
                        .orElseThrow(() -> new NoSuchElementException(String.format("Tag id %d does not exist", id))));
            }
            reviewerEntity.setTags(tagEntityList);
        }

        if (input.facultyId() != null) {
            FacultyEntity facultyEntity = facultyRepo.findById(input.facultyId())
                    .orElseThrow(() -> new NoSuchElementException(String.format("Faculty id %d does not exist", input.facultyId())));
            reviewerEntity.setFaculty(facultyEntity);
        }

        if (input.titleId() != null) {
            TitleEntity titleEntity = (TitleEntity) dictionaryRepo.findByTypeAndId("title", input.titleId())
                    .orElseThrow(() -> new NoSuchElementException(String.format("Title id %d does not exist", input.titleId())));
            reviewerEntity.setTitle(titleEntity);
        }

        if (input.name() != null)
            reviewerEntity.setName(input.name());
        if (input.surname() != null)
            reviewerEntity.setSurname(input.surname());

        return ReviewerMapper.INSTANCE.toGetDTO(reviewerEntity);
    }

    @Override
    public void delete(Long id) {
        reviewerRepo.deleteById(id);
    }
}
