package pl.polsl.reviewersapp.api.model.dto.thesis;

public record ThesisAddDTO (
        String topic,
        String authorAlbumNumber,
        String keywords,
        String summary,
        Long reviewerId
) {}
