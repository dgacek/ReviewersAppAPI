package pl.polsl.reviewersapp.api.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.reviewersapp.api.model.entity.DictionaryEntity;

import java.util.List;
import java.util.Optional;

public interface DictionaryRepo extends JpaRepository<DictionaryEntity, Long> {
    @Query(value = "SELECT * " +
            "FROM Dictionary_Entity " +
            "WHERE dtype = :type", nativeQuery = true)
    List<DictionaryEntity> findAllByType(@Param("type") String type);

    @Query(value = "SELECT * " +
            "FROM Dictionary_Entity " +
            "WHERE dtype = :type " +
            "AND id = :id", nativeQuery = true)
    Optional<DictionaryEntity> findByTypeAndId(@Param("type") String type, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE " +
            "FROM Dictionary_Entity " +
            "WHERE dtype = :type " +
            "AND id = :id", nativeQuery = true)
    void deleteByTypeAndId(@Param("type") String type, @Param("id") Long id);
}
