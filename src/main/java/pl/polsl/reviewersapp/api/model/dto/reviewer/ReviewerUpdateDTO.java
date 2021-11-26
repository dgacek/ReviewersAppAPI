package pl.polsl.reviewersapp.api.model.dto.reviewer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewerUpdateDTO {
    private Long id;
    private String name;
    private String surname;
    private String title;
    private Long facultyId;
    private List<Long> tagIdList;
}
