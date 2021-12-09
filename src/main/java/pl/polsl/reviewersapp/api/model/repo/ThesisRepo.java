package pl.polsl.reviewersapp.api.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.polsl.reviewersapp.api.model.entity.ThesisEntity;

import java.util.Optional;

public interface ThesisRepo extends JpaRepository<ThesisEntity, Long> {
    @Query(value = "SELECT id " +
            "FROM THESIS_ENTITY " +
            "WHERE id > :id " +
            "LIMIT 1", nativeQuery = true)
    Optional<Long> getNextRecordId(@Param("id") Long id);
}
