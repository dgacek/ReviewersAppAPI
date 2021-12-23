package pl.polsl.reviewersapp.api.service;

import org.springframework.web.multipart.MultipartFile;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisAddDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisGetDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisUpdateDTO;

import java.io.IOException;
import java.util.List;

public interface ThesisService {
    ThesisGetDTO get(Long id);
    List<ThesisGetDTO> getAll();
    ThesisGetDTO add(ThesisAddDTO input);
    ThesisGetDTO update(ThesisUpdateDTO input);
    void delete(Long id);
    void importExcel(MultipartFile file) throws IOException;
}
