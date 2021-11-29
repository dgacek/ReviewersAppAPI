package pl.polsl.reviewersapp.api.model.dto.thesis;

import pl.polsl.reviewersapp.api.model.dto.reviewer.ReviewerGetDTO;

public record ThesisGetDTO (
        Long id,
        String authorAlbumNumber,
        String topic,
        ReviewerGetDTO reviewer
) {}
