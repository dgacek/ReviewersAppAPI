package pl.polsl.reviewersapp.api.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.reviewersapp.api.model.dto.mapper.ThesisMapper;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisAddDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisGetDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.ReviewerEntity;
import pl.polsl.reviewersapp.api.model.entity.ThesisEntity;
import pl.polsl.reviewersapp.api.model.repo.ReviewerRepo;
import pl.polsl.reviewersapp.api.model.repo.ThesisRepo;
import pl.polsl.reviewersapp.api.service.ThesisService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ThesisServiceImpl implements ThesisService {
    private final ThesisRepo thesisRepo;
    private final ReviewerRepo reviewerRepo;

    @Override
    public ThesisGetDTO get(Long id) {
        return ThesisMapper.INSTANCE.toGetUpdateDTO(thesisRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Thesis id %d does not exist", id))));
    }

    @Override
    public List<ThesisGetDTO> getAll() {
        return ThesisMapper.INSTANCE.toGetUpdateDTOList(thesisRepo.findAll());
    }

    @Override
    public ThesisGetDTO add(ThesisAddDTO input) {
        ReviewerEntity reviewerEntity = reviewerRepo.findById(input.getReviewerId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Reviewer id %d does not exist", input.getReviewerId())));
        return ThesisMapper.INSTANCE.toGetUpdateDTO(thesisRepo.save(ThesisEntity.builder()
                .authorName(input.getAuthorName())
                .authorSurname(input.getAuthorSurname())
                .topic(input.getTopic())
                .reviewer(reviewerEntity)
                .build()));
    }

    @Override
    @Transactional
    public ThesisGetDTO update(ThesisUpdateDTO input) {
        ThesisEntity thesisEntity = thesisRepo.findById(input.getId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Thesis id %d does not exist", input.getId())));
        ReviewerEntity reviewerEntity = reviewerRepo.findById(input.getReviewerId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Reviewer id %d does not exist", input.getId())));
        thesisEntity.setAuthorName(input.getAuthorName());
        thesisEntity.setAuthorSurname(input.getAuthorSurname());
        thesisEntity.setTopic(input.getTopic());
        thesisEntity.setReviewer(reviewerEntity);
        return ThesisMapper.INSTANCE.toGetUpdateDTO(thesisEntity);
    }

    @Override
    public void delete(Long id) {
        thesisRepo.deleteById(id);
    }
}
