package pl.polsl.reviewersapp.api.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.reviewersapp.api.model.entity.TagEntity;

public interface TagRepo extends JpaRepository<TagEntity, Long> {
}
