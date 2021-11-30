package pl.polsl.reviewersapp.api.model.dto.user;

public record UserUpdateDTO(
        Long id,
        String username,
        String password
) {
}
