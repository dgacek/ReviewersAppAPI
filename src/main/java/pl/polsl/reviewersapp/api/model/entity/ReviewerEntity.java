package pl.polsl.reviewersapp.api.model.entity;

import lombok.*;
import pl.polsl.reviewersapp.api.model.entity.dictionary.TagEntity;
import pl.polsl.reviewersapp.api.model.entity.dictionary.TitleEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReviewerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviewer_seq_gen")
    @SequenceGenerator(name = "reviewer_seq_gen", sequenceName = "reviewer_id_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY)
    private TitleEntity title;

    @ManyToOne(fetch = FetchType.LAZY)
    private FacultyEntity faculty;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<TagEntity> tags;
}
