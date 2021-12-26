package pl.polsl.reviewersapp.api.service.implementation;

import lombok.AllArgsConstructor;
import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.reviewersapp.api.model.entity.dictionary.TagEntity;
import pl.polsl.reviewersapp.api.model.entity.dictionary.TitleEntity;
import pl.polsl.reviewersapp.api.model.mapper.ReviewerMapper;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerAddDTO;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerGetDTO;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.FacultyEntity;
import pl.polsl.reviewersapp.api.model.entity.ReviewerEntity;
import pl.polsl.reviewersapp.api.model.repo.DictionaryRepo;
import pl.polsl.reviewersapp.api.model.repo.FacultyRepo;
import pl.polsl.reviewersapp.api.model.repo.ReviewerRepo;
import pl.polsl.reviewersapp.api.service.ReviewerService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class ReviewerServiceImpl implements ReviewerService {
    private final ReviewerRepo reviewerRepo;
    private final FacultyRepo facultyRepo;
    private final DictionaryRepo dictionaryRepo;

    @Override
    public ReviewerGetDTO get(Long id) {
        return ReviewerMapper.INSTANCE.toGetDTO(reviewerRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Reviewer id %d does not exist", id))));
    }

    @Override
    public List<ReviewerGetDTO> getAll() {
        return ReviewerMapper.INSTANCE.toGetDTOList(reviewerRepo.findAll());
    }

    @Override
    public ReviewerGetDTO add(ReviewerAddDTO input) {
        FacultyEntity facultyEntity = facultyRepo.findById(input.facultyId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Faculty id %d does not exist", input.facultyId())));

        TitleEntity titleEntity = (TitleEntity) dictionaryRepo.findByTypeAndId("title", input.titleId())
                .orElseThrow(() -> new NoSuchElementException(String.format("Title id %d does not exist", input.titleId())));

        List<TagEntity> tagEntityList = new ArrayList<>();
        for (Long id : input.tagIdList()) {
            tagEntityList.add((TagEntity) dictionaryRepo.findByTypeAndId("tag", id)
                    .orElseThrow(() -> new NoSuchElementException(String.format("Tag id %d does not exist", id))));
        }

        return ReviewerMapper.INSTANCE.toGetDTO(reviewerRepo.save(ReviewerEntity.builder()
                .name(input.name())
                .surname(input.surname())
                .email(input.email())
                .faculty(facultyEntity)
                .title(titleEntity)
                .tags(tagEntityList)
                .build()));
    }

    @Override
    @Transactional
    public ReviewerGetDTO update(ReviewerUpdateDTO input) {
        ReviewerEntity reviewerEntity = reviewerRepo.findById(input.id())
                .orElseThrow(() -> new NoSuchElementException(String.format("Reviewer id %d does not exist", input.facultyId())));

        if (input.tagIdList() != null) {
            List<TagEntity> tagEntityList = new ArrayList<>();
            for (Long id : input.tagIdList()) {
                tagEntityList.add((TagEntity) dictionaryRepo.findByTypeAndId("tag", id)
                        .orElseThrow(() -> new NoSuchElementException(String.format("Tag id %d does not exist", id))));
            }
            reviewerEntity.setTags(tagEntityList);
        }

        if (input.facultyId() != null) {
            FacultyEntity facultyEntity = facultyRepo.findById(input.facultyId())
                    .orElseThrow(() -> new NoSuchElementException(String.format("Faculty id %d does not exist", input.facultyId())));
            reviewerEntity.setFaculty(facultyEntity);
        }

        if (input.titleId() != null) {
            TitleEntity titleEntity = (TitleEntity) dictionaryRepo.findByTypeAndId("title", input.titleId())
                    .orElseThrow(() -> new NoSuchElementException(String.format("Title id %d does not exist", input.titleId())));
            reviewerEntity.setTitle(titleEntity);
        }

        if (input.name() != null)
            reviewerEntity.setName(input.name());
        if (input.surname() != null)
            reviewerEntity.setSurname(input.surname());
        if (input.email() != null)
            reviewerEntity.setEmail(input.email());

        return ReviewerMapper.INSTANCE.toGetDTO(reviewerEntity);
    }

    @Override
    public void delete(Long id) {
        reviewerRepo.deleteById(id);
    }

    @Override
    public void importExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(file.getBytes()));
        Sheet mainSheet = workbook.getSheetAt(0);
        Sheet facultySheet = workbook.getSheetAt(1);
        HashSet<String> titles = new HashSet<>();
        HashSet<String> tags = new HashSet<>();

        // READ DATA FROM WORKBOOK

        HashSet<Pair<String, String>> faculties = new HashSet<>(facultySheet.getPhysicalNumberOfRows());

        for (Row row : facultySheet) {
            Cell symbolCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Cell nameCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

            if (symbolCell == null || nameCell == null) {
                throw new IOException(String.format("Missing attribute at row %d in faculty sheet", row.getRowNum()+1));
            }

            faculties.add(new Pair<>(symbolCell.getStringCellValue(), nameCell.getStringCellValue()));
        }

        /*
         * HashMap structure:
         * NAME - String
         * SURNAME - String
         * TITLE_ID - Long
         * TITLE_KEY - String
         * EMAIL - String
         * FACULTY_ID - Long
         * FACULTY_KEY - String
         * TAG_KEYS - ArrayList(String)
         * TAG_IDS - ArrayLst(Long)
         */
        ArrayList<HashMap<String, Object>> reviewers = new ArrayList<>();

        for (Row row : mainSheet) {
            Cell nameCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Cell surnameCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Cell titleCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Cell facultyCell = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Cell emailCell = row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Cell tagCell = row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

            if (nameCell == null || surnameCell == null || titleCell == null || facultyCell == null) {
                throw new IOException(String.format("Missing attribute at row %d in reviewer sheet", row.getRowNum()+1));
            }


        }

        // INSERT INTO DATABASE


    }
}
