package pl.polsl.reviewersapp.api.model.dto.thesis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerGetDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisGetUpdateDTO {
    private Long id;
    private String name;
    private String surname;
    private String topic;
    private ReviewerGetDTO reviewer;
}
