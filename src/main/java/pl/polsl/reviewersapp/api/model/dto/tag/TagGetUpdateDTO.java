package pl.polsl.reviewersapp.api.model.dto.tag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TagGetUpdateDTO {
    private Long id;
    private String name;
}
