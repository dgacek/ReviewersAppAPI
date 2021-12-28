package pl.polsl.reviewersapp.api.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.polsl.reviewersapp.api.model.entity.ThesisEntity;

import javax.transaction.Transactional;

public interface ThesisRepo extends JpaRepository<ThesisEntity, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Thesis_Entity " +
            "SET REVIEWER_ID = NULL " +
            "WHERE REVIEWER_ID IS NOT NULL", nativeQuery = true)
    void clearAttachments();
}
