package pl.polsl.reviewersapp.api.model.entity;

import lombok.*;

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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private FacultyEntity faculty;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<TagEntity> tags;
}
