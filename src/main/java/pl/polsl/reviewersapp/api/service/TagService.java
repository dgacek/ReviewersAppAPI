package pl.polsl.reviewersapp.api.service;

import pl.polsl.reviewersapp.api.model.dto.tag.TagAddDTO;
import pl.polsl.reviewersapp.api.model.dto.tag.TagGetUpdateDTO;

import java.util.List;

public interface TagService {
    TagGetUpdateDTO get(Long id);
    List<TagGetUpdateDTO> getAll();
    TagGetUpdateDTO add(TagAddDTO input);
    TagGetUpdateDTO update(TagGetUpdateDTO input);
    void delete(Long id);
}
