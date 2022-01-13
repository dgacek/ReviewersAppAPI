package pl.polsl.reviewersapp.api.service.implementation;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.polsl.reviewersapp.api.model.mapper.ThesisMapper;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisAddDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisGetDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.ReviewerEntity;
import pl.polsl.reviewersapp.api.model.entity.ThesisEntity;
import pl.polsl.reviewersapp.api.model.repo.ReviewerRepo;
import pl.polsl.reviewersapp.api.model.repo.ThesisRepo;
import pl.polsl.reviewersapp.api.service.ThesisService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ThesisServiceImpl implements ThesisService {
    private final ThesisRepo thesisRepo;
    private final ReviewerRepo reviewerRepo;

    @Override
    public ThesisGetDTO get(Long id) {
        return ThesisMapper.INSTANCE.toGetDTO(thesisRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Thesis id %d does not exist", id))));
    }

    @Override
    public List<ThesisGetDTO> getAll() {
        return ThesisMapper.INSTANCE.toGetDTOList(thesisRepo.findAll());
    }

    @Override
    public ThesisGetDTO add(ThesisAddDTO input) {
        ReviewerEntity reviewerEntity = null;

        if (input.reviewerId() != null) {
            reviewerEntity = reviewerRepo.findById(input.reviewerId())
                    .orElseThrow(() -> new NoSuchElementException(String.format("Reviewer id %d does not exist", input.reviewerId())));
        }

        return ThesisMapper.INSTANCE.toGetDTO(thesisRepo.save(ThesisEntity.builder()
                .authorAlbumNumber(input.authorAlbumNumber())
                .topic(input.topic())
                .keywords(input.keywords())
                .summary(input.summary())
                .reviewer(reviewerEntity)
                .build()));
    }

    @Override
    @Transactional
    public ThesisGetDTO update(ThesisUpdateDTO input) {
        ThesisEntity thesisEntity = thesisRepo.findById(input.id())
                .orElseThrow(() -> new NoSuchElementException(String.format("Thesis id %d does not exist", input.id())));

        if (input.reviewerId() != null) {
            ReviewerEntity reviewerEntity = reviewerRepo.findById(input.reviewerId())
                    .orElseThrow(() -> new NoSuchElementException(String.format("Reviewer id %d does not exist", input.id())));
            thesisEntity.setReviewer(reviewerEntity);
        } else
            thesisEntity.setReviewer(null);

        if (input.authorAlbumNumber() != null)
            thesisEntity.setAuthorAlbumNumber(input.authorAlbumNumber());
        if (input.topic() != null)
            thesisEntity.setTopic(input.topic());
        if (input.keywords() != null)
            thesisEntity.setKeywords(input.keywords());
        if (input.summary() != null)
            thesisEntity.setSummary(input.summary());

        return ThesisMapper.INSTANCE.toGetDTO(thesisEntity);
    }

    @Override
    public void delete(Long id) {
        thesisRepo.deleteById(id);
    }

    @Override
    public void importExcel(MultipartFile file) throws IOException {
        Workbook workbook = new XSSFWorkbook(new ByteArrayInputStream(file.getBytes()));
        Sheet sheet = workbook.getSheetAt(0);
        ArrayList<HashMap<String, String>> values = new ArrayList<>(sheet.getPhysicalNumberOfRows());
        for (Row row : sheet) {
            Cell numberCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Cell topicCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Cell keywordsCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            Cell summaryCell = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (numberCell == null || topicCell == null) { //required values
                throw new IOException(String.format("Missing attribute at row %d", row.getRowNum() + 1));
            }

            HashMap<String, String> tmp = new HashMap<>();
            // excel might set the data type to numeric in this cell so a type check is needed
            tmp.put("number", numberCell.getCellType().equals(CellType.NUMERIC) ? String.valueOf((int) numberCell.getNumericCellValue()) : numberCell.getStringCellValue());
            tmp.put("topic", topicCell.getStringCellValue());
            tmp.put("keywords", keywordsCell.getStringCellValue());
            tmp.put("summary", summaryCell.getStringCellValue());
            values.add(tmp);
        }
        for (HashMap<String, String> value : values) {
            thesisRepo.save(ThesisEntity.builder()
                            .authorAlbumNumber(value.get("number"))
                            .topic(value.get("topic"))
                            .keywords(value.get("keywords"))
                            .summary(value.get("summary"))
                    .build());
        }
    }

    @Override
    public byte[] exportToExcel() throws IOException {
        List<ThesisGetDTO> theses = ThesisMapper.INSTANCE.toGetDTOList(thesisRepo.findAll()).stream().filter(item -> item.reviewer() != null).toList();
        if (theses.isEmpty())
            throw new IOException("No attachments exist");
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();

            int rowNumber = 0;
            for (ThesisGetDTO thesis : theses) {
                Row row = sheet.createRow(rowNumber++);
                Cell albumNumberCell = row.createCell(0);
                Cell topicCell = row.createCell(1);
                Cell reviewerNameCell = row.createCell(2);
                Cell reviewerSurnameCell = row.createCell(3);
                Cell reviewerTitleCell = row.createCell(4);
                Cell reviewerFacultySymbolCell = row.createCell(5);
                Cell reviewerFacultyNameCell = row.createCell(6);
                Cell reviewerEmailCell = row.createCell(7);

                albumNumberCell.setCellValue(thesis.authorAlbumNumber());
                topicCell.setCellValue(thesis.topic());
                reviewerNameCell.setCellValue(thesis.reviewer().name());
                reviewerSurnameCell.setCellValue(thesis.reviewer().surname());
                reviewerTitleCell.setCellValue(thesis.reviewer().title().name());
                reviewerFacultySymbolCell.setCellValue(thesis.reviewer().faculty().symbol());
                reviewerFacultyNameCell.setCellValue(thesis.reviewer().faculty().name());
                if (thesis.reviewer().email() != null)
                    reviewerEmailCell.setCellValue(thesis.reviewer().email());
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
}
