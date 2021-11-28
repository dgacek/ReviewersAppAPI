package pl.polsl.reviewersapp.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.reviewersapp.api.model.dto.faculty.FacultyAddDTO;
import pl.polsl.reviewersapp.api.model.dto.faculty.FacultyGetUpdateDTO;
import pl.polsl.reviewersapp.api.service.FacultyService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/faculties")
public record FacultyController (
        FacultyService facultyService
) {
    @GetMapping
    public ResponseEntity<List<FacultyGetUpdateDTO>> get(@RequestParam(required = false) Long id) {
        try {
            if (id == null)
                return new ResponseEntity<>(facultyService.getAll(), HttpStatus.OK);
            return new ResponseEntity<>(Collections.singletonList(facultyService.get(id)), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping
    public ResponseEntity<FacultyGetUpdateDTO> add(@RequestBody FacultyAddDTO input) {
        try {
            return new ResponseEntity<>(facultyService.add(input), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping
    public ResponseEntity<FacultyGetUpdateDTO> update(@RequestBody FacultyGetUpdateDTO input) {
        try {
            return new ResponseEntity<>(facultyService.update(input), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        facultyService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
