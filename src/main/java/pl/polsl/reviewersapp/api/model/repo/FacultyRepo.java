package pl.polsl.reviewersapp.api.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.reviewersapp.api.model.entity.FacultyEntity;

public interface FacultyRepo extends JpaRepository<FacultyEntity, Long> {
}
