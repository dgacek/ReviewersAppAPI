package pl.polsl.reviewersapp.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisAddDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisGetDTO;
import pl.polsl.reviewersapp.api.model.dto.thesis.ThesisUpdateDTO;
import pl.polsl.reviewersapp.api.service.ThesisService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/theses")
public record ThesisController (
        ThesisService thesisService
)
{
    @GetMapping
    public ResponseEntity<List<ThesisGetDTO>> get(@RequestParam(required = false) Long id) {
        try {
            if (id == null)
                return new ResponseEntity<>(thesisService.getAll(), HttpStatus.OK);
            return new ResponseEntity<>(Collections.singletonList(thesisService.get(id)), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping
    public ResponseEntity<ThesisGetDTO> add(@RequestBody ThesisAddDTO input) {
        try {
            return new ResponseEntity<>(thesisService.add(input), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping
    public ResponseEntity<ThesisGetDTO> update(@RequestBody ThesisUpdateDTO input) {
        try {
            return new ResponseEntity<>(thesisService.update(input), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        thesisService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importExcel(@RequestParam("file") MultipartFile file) {
        try {
            thesisService.importExcel(file);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody byte[] exportToExcel() {
        try {
            return thesisService.exportToExcel();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
