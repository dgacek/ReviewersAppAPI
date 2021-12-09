package pl.polsl.reviewersapp.api.service;

import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisAddDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisGetDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisUpdateDTO;

import java.util.List;

public interface ThesisService {
    ThesisGetDTO get(Long id);
    List<ThesisGetDTO> getAll();
    ThesisGetDTO add(ThesisAddDTO input);
    ThesisGetDTO update(ThesisUpdateDTO input);
    void delete(Long id);
    Long getNextRecordId(Long id);
}
