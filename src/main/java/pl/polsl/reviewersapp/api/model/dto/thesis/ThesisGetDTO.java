package pl.polsl.reviewersapp.api.model.dto.thesis;

import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerGetDTO;

public record ThesisGetDTO (
        Long id,
        String authorName,
        String authorSurname,
        String topic,
        ReviewerGetDTO reviewer
) {}
