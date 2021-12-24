package pl.polsl.reviewersapp.api.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ThesisEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thesisSeqGen")
    @SequenceGenerator(name = "thesisSeqGen", sequenceName = "thesis_id_seq", allocationSize = 1)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String topic;

    @Column(nullable = false)
    private String authorAlbumNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    private ReviewerEntity reviewer;
}
