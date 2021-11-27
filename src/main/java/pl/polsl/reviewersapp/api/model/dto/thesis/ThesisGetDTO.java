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
public class ThesisGetDTO {
    private Long id;
    private String authorName;
    private String authorSurname;
    private String topic;
    private ReviewerGetDTO reviewer;
}
