package pl.polsl.reviewersapp.api.model.dto.thesis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ThesisAddDTO {
    private String topic;
    private String authorName;
    private String authorSurname;
    private Long reviewerId;
}
