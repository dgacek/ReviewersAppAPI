package pl.polsl.reviewersapp.api.service;

import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisAddDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisGetUpdateDTO;

import java.util.List;

public interface ThesisService {
    ThesisGetUpdateDTO get(Long id);
    List<ThesisGetUpdateDTO> getAll();
    ThesisGetUpdateDTO add(ThesisAddDTO input);
    ThesisGetUpdateDTO update(ThesisGetUpdateDTO input);
    void delete(Long id);
}
