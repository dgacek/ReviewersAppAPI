package pl.polsl.reviewersapp.api.model.entity.dictionary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.reviewersapp.api.model.entity.DictionaryEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("title")
@NoArgsConstructor
@Setter
@Getter
public class TitleEntity extends DictionaryEntity {
    public TitleEntity(String name) {
        this.name = name;
    }
}
