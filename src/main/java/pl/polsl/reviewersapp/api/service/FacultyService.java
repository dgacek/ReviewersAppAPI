package pl.polsl.reviewersapp.api.service;

import pl.polsl.reviewersapp.api.model.dto.faculty.FacultyAddDTO;
import pl.polsl.reviewersapp.api.model.dto.faculty.FacultyGetUpdateDTO;

import java.util.List;

public interface FacultyService {
    FacultyGetUpdateDTO get(Long id);
    List<FacultyGetUpdateDTO> getAll();
    FacultyGetUpdateDTO add(FacultyAddDTO input);
    FacultyGetUpdateDTO update(FacultyGetUpdateDTO input);
    void delete(Long id);
}
