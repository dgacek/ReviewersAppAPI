package pl.polsl.reviewersapp.api.model.dto.thesis;

public record ThesisUpdateDTO (
        Long id,
        String authorAlbumNumber,
        String topic,
        String keywords,
        String summary,
        Long reviewerId
) {}
