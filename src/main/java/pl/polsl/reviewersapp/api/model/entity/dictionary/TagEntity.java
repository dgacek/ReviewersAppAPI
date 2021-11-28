package pl.polsl.reviewersapp.api.model.entity.dictionary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.polsl.reviewersapp.api.model.entity.DictionaryEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("tag")
@Getter
@Setter
@NoArgsConstructor
public class TagEntity extends DictionaryEntity {
    public TagEntity(String name) {
        this.name = name;
    }
}
