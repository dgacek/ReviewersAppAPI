package pl.polsl.reviewersapp.api.model.dto.faculty;

public record FacultyGetUpdateDTO (
        Long id,
        String name,
        String symbol
) {}
