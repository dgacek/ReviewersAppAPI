package pl.polsl.reviewersapp.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerAddDTO;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerGetDTO;
import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerUpdateDTO;
import pl.polsl.reviewersapp.api.service.ReviewerService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/reviewers")
public record ReviewerController (
        ReviewerService reviewerService
)
{
    @GetMapping
    public ResponseEntity<List<ReviewerGetDTO>> get(@RequestParam(required = false) Long id) {
        try {
            if (id == null)
                return new ResponseEntity<>(reviewerService.getAll(), HttpStatus.OK);
            return new ResponseEntity<>(Collections.singletonList(reviewerService.get(id)), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping
    public ResponseEntity<ReviewerGetDTO> add(@RequestBody ReviewerAddDTO input) {
        try {
            return new ResponseEntity<>(reviewerService.add(input), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping
    public ResponseEntity<ReviewerGetDTO> update(@RequestBody ReviewerUpdateDTO input) {
        try {
            return new ResponseEntity<>(reviewerService.update(input), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        reviewerService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
