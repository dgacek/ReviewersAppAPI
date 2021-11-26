package pl.polsl.reviewersapp.api.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.reviewersapp.api.model.entity.ReviewerEntity;

public interface ReviewerRepo extends JpaRepository<ReviewerEntity, Long> {
}
