package pl.polsl.reviewersapp.api.model.dto.thesis;

public record ThesisUpdateDTO (
        Long id,
        String authorName,
        String authorSurname,
        String topic,
        Long reviewerId
) {}
