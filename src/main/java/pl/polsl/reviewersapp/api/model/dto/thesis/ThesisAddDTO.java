package pl.polsl.reviewersapp.api.model.dto.thesis;

public record ThesisAddDTO (
        String topic,
        String authorAlbumNumber,
        Long reviewerId
) {}
