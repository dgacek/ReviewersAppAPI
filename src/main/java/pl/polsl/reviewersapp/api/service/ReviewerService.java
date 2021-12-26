package pl.polsl.reviewersapp.api.service;

import org.springframework.web.multipart.MultipartFile;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerAddDTO;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerGetDTO;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerUpdateDTO;

import java.io.IOException;
import java.util.List;

public interface ReviewerService {
    ReviewerGetDTO get(Long id);
    List<ReviewerGetDTO> getAll();
    ReviewerGetDTO add(ReviewerAddDTO input);
    ReviewerGetDTO update(ReviewerUpdateDTO input);
    void delete(Long id);
    void importExcel(MultipartFile file) throws IOException;
}
