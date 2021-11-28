package pl.polsl.reviewersapp.api.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.reviewersapp.api.model.dto.dictionary.DictionaryAddDTO;
import pl.polsl.reviewersapp.api.model.dto.dictionary.DictionaryGetUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.DictionaryEntity;
import pl.polsl.reviewersapp.api.model.entity.dictionary.TagEntity;
import pl.polsl.reviewersapp.api.model.entity.dictionary.TitleEntity;
import pl.polsl.reviewersapp.api.model.mapper.DictionaryMapper;
import pl.polsl.reviewersapp.api.model.repo.DictionaryRepo;
import pl.polsl.reviewersapp.api.service.DictionaryService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepo dictionaryRepo;

    @Override
    public DictionaryGetUpdateDTO get(String typeName, Long id) {
        return DictionaryMapper.INSTANCE.toGetUpdateDTO(dictionaryRepo.findByTypeAndId(typeName, id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Dictionary entry type %s id %d does not exist", typeName, id))));
    }

    @Override
    public List<DictionaryGetUpdateDTO> getAll(String typeName) {
        return DictionaryMapper.INSTANCE.toGetUpdateDTOList(dictionaryRepo.findAllByType(typeName));
    }

    @Override
    public DictionaryGetUpdateDTO add(String typeName, DictionaryAddDTO input) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return DictionaryMapper.INSTANCE.toGetUpdateDTO(dictionaryRepo.save(parseTypeName(typeName)
                .getDeclaredConstructor(String.class)
                .newInstance(input.name())));
    }

    @Override
    @Transactional
    public DictionaryGetUpdateDTO update(String typeName, DictionaryGetUpdateDTO input) {
        DictionaryEntity dictionaryEntity = dictionaryRepo.findByTypeAndId(typeName, input.id())
                .orElseThrow(() -> new NoSuchElementException(String.format("Dictionary entry type %s id %d does not exist", typeName, input.id())));

        if (input.name() != null)
            dictionaryEntity.setName(input.name());

        return DictionaryMapper.INSTANCE.toGetUpdateDTO(dictionaryEntity);
    }

    @Override
    public void delete(String typeName, Long id) {
        dictionaryRepo.deleteByTypeAndId(typeName, id);
    }

    private Class<? extends DictionaryEntity> parseTypeName(String typeName) throws ClassNotFoundException {
        return switch (typeName) {
            case "tag" -> TagEntity.class;
            case "title" -> TitleEntity.class;
            default -> throw new ClassNotFoundException(String.format("\"%s\" is not a valid dictionary type name", typeName));
        };
    }
}
