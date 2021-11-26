package pl.polsl.reviewersapp.api.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.reviewersapp.api.model.dto.mapper.TagMapper;
import pl.polsl.reviewersapp.api.model.dto.tag.TagAddDTO;
import pl.polsl.reviewersapp.api.model.dto.tag.TagGetUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.TagEntity;
import pl.polsl.reviewersapp.api.model.repo.TagRepo;
import pl.polsl.reviewersapp.api.service.TagService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private TagRepo tagRepo;

    @Override
    public TagGetUpdateDTO get(Long id) {
        return TagMapper.INSTANCE.toGetUpdateDTO(tagRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Tag id %d does not exist", id))));
    }

    @Override
    public List<TagGetUpdateDTO> getAll() {
        return TagMapper.INSTANCE.toGetUpdateDTOList(tagRepo.findAll());
    }

    @Override
    public TagGetUpdateDTO add(TagAddDTO input) {
        return TagMapper.INSTANCE.toGetUpdateDTO(tagRepo.save(TagEntity.builder()
                .name(input.getName())
                .build()));
    }

    @Override
    @Transactional
    public TagGetUpdateDTO update(TagGetUpdateDTO input) {
        TagEntity tagEntity = tagRepo.findById(input.getId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Tag id %d does not exist", input.getId())));
        tagEntity.setName(input.getName());
        return TagMapper.INSTANCE.toGetUpdateDTO(tagEntity);
    }

    @Override
    public void delete(Long id) {
        tagRepo.deleteById(id);
    }
}
