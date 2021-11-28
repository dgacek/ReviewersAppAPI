package pl.polsl.reviewersapp.api.service;

import pl.polsl.reviewersapp.api.model.dto.dictionary.DictionaryAddDTO;
import pl.polsl.reviewersapp.api.model.dto.dictionary.DictionaryGetUpdateDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface DictionaryService {
    DictionaryGetUpdateDTO get(String typeName, Long id);
    List<DictionaryGetUpdateDTO> getAll(String typeName);
    DictionaryGetUpdateDTO add(String typeName, DictionaryAddDTO input) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
    DictionaryGetUpdateDTO update(String typeName, DictionaryGetUpdateDTO input);
    void delete(String typeName, Long id);
}
