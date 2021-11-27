package pl.polsl.reviewersapp.api.model.dto.thesis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ThesisUpdateDTO {
    private Long id;
    private String authorName;
    private String authorSurname;
    private String topic;
    private Long reviewerId;
}
