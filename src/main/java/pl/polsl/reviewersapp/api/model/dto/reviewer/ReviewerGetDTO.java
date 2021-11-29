package pl.polsl.reviewersapp.api.model.dto.reviewer;

import pl.polsl.reviewersapp.api.model.dto.dictionary.DictionaryGetUpdateDTO;
import pl.polsl.reviewersapp.api.model.dto.faculty.FacultyGetUpdateDTO;

import java.util.List;

public record ReviewerGetDTO (
        Long id,
        String name,
        String surname,
        String email,
        DictionaryGetUpdateDTO title,
        FacultyGetUpdateDTO faculty,
        List<DictionaryGetUpdateDTO> tags
) {}
