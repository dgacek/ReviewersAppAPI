package pl.polsl.reviewersapp.api.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.reviewersapp.api.model.entity.ThesisEntity;

public interface ThesisRepo extends JpaRepository<ThesisEntity, Long> {
}
