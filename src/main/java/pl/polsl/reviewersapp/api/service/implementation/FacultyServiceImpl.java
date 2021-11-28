package pl.polsl.reviewersapp.api.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.reviewersapp.api.model.dto.faculty.FacultyAddDTO;
import pl.polsl.reviewersapp.api.model.dto.faculty.FacultyGetUpdateDTO;
import pl.polsl.reviewersapp.api.model.mapper.FacultyMapper;
import pl.polsl.reviewersapp.api.model.entity.FacultyEntity;
import pl.polsl.reviewersapp.api.model.repo.FacultyRepo;
import pl.polsl.reviewersapp.api.service.FacultyService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepo facultyRepo;

    @Override
    public FacultyGetUpdateDTO get(Long id) {
        return FacultyMapper.INSTANCE.toGetUpdateDTO(facultyRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Faculty id %d does not exist", id))));
    }

    @Override
    public List<FacultyGetUpdateDTO> getAll() {
        return FacultyMapper.INSTANCE.toGetUpdateDTOList(facultyRepo.findAll());
    }

    @Override
    public FacultyGetUpdateDTO add(FacultyAddDTO input) {
        return FacultyMapper.INSTANCE.toGetUpdateDTO(facultyRepo.save(FacultyEntity.builder()
                .name(input.name())
                .symbol(input.symbol())
                .build()));
    }

    @Override
    @Transactional
    public FacultyGetUpdateDTO update(FacultyGetUpdateDTO input) {
        FacultyEntity facultyEntity = facultyRepo.findById(input.id())
                .orElseThrow(() -> new NoSuchElementException(String.format("Faculty id %d does not exist", input.id())));

        if (input.name() != null)
            facultyEntity.setName(input.name());
        if (input.symbol() != null)
            facultyEntity.setSymbol(input.symbol());

        return FacultyMapper.INSTANCE.toGetUpdateDTO(facultyEntity);
    }

    @Override
    public void delete(Long id) {
        facultyRepo.deleteById(id);
    }
}
