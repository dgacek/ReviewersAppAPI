package pl.polsl.reviewersapp.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import pl.polsl.reviewersapp.api.model.entity.ThesisEntity;
import pl.polsl.reviewersapp.api.model.repo.ThesisRepo;

import java.util.List;

@DataJpaTest
class ApiApplicationTests {

	@Autowired
	ThesisRepo thesisRepo;

	@Test
	@WithMockUser(username = "admin", password = "admin")
	void getNextRecordIdTest() {
		// --------- SETUP ------------
		thesisRepo.save(ThesisEntity.builder()
				.topic("test1")
				.authorAlbumNumber("52354")
				.build());
		thesisRepo.save(ThesisEntity.builder()
				.topic("test2")
				.authorAlbumNumber("543543")
				.build());
		thesisRepo.save(ThesisEntity.builder()
				.topic("test3")
				.authorAlbumNumber("643534")
				.build());

		List<ThesisEntity> records = thesisRepo.findAll(Sort.by(Sort.Direction.ASC, "id"));
		Long input = records.get(1).getId();
		Long expectedOutput = records.get(2).getId();

		// ---------- CALL -----------
		Long output = thesisRepo.getNextRecordId(input).orElseThrow();

		// ---------- CHECK ----------
		Assertions.assertEquals(expectedOutput, output);

	}

}
