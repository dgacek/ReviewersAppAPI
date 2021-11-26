package pl.polsl.reviewersapp.api.model.dto.faculty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacultyGetUpdateDTO {
    private Long id;
    private String name;
    private String symbol;
}
