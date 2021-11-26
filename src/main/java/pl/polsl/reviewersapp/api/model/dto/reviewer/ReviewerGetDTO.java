package pl.polsl.reviewersapp.api.model.dto.reviewer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.reviewersapp.api.model.dto.faculty.FacultyGetUpdateDTO;
import pl.polsl.reviewersapp.api.model.dto.tag.TagGetUpdateDTO;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewerGetDTO {
    private Long id;
    private String name;
    private String surname;
    private String title;
    private FacultyGetUpdateDTO faculty;
    private List<TagGetUpdateDTO> tags;
}
