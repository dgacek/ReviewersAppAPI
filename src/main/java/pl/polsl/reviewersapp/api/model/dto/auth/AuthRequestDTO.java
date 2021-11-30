package pl.polsl.reviewersapp.api.model.dto.auth;

public record AuthRequestDTO(
        String username,
        String password
) {
}
