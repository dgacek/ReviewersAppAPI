package pl.polsl.reviewersapp.api.model.dto.reviewer;

import java.util.List;

public record ReviewerAddDTO (
        String name,
        String surname,
        String email,
        Long titleId,
        Long facultyId,
        List<Long> tagIdList
) {}
