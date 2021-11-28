package pl.polsl.reviewersapp.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.reviewersapp.api.model.dto.dictionary.DictionaryAddDTO;
import pl.polsl.reviewersapp.api.model.dto.dictionary.DictionaryGetUpdateDTO;
import pl.polsl.reviewersapp.api.service.DictionaryService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("/dictionary")
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @GetMapping
    public ResponseEntity<List<DictionaryGetUpdateDTO>> get(@RequestParam String type, @RequestParam(required = false) Long id) {
        try {
            if (id == null)
                return new ResponseEntity<>(dictionaryService.getAll(type), HttpStatus.OK);
            return new ResponseEntity<>(Collections.singletonList(dictionaryService.get(type, id)), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping
    public ResponseEntity<DictionaryGetUpdateDTO> add(@RequestParam String type, @RequestBody DictionaryAddDTO input) {
        try {
            return new ResponseEntity<>(dictionaryService.add(type, input), HttpStatus.CREATED);
        } catch (NoSuchElementException | ClassNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping
    public ResponseEntity<DictionaryGetUpdateDTO> update(@RequestParam String type, @RequestBody DictionaryGetUpdateDTO input) {
        try {
            return new ResponseEntity<>(dictionaryService.update(type, input), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String type, @RequestParam Long id) {
        dictionaryService.delete(type, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
