package pl.polsl.reviewersapp.api.model.dto.user;

public record UserChangePasswordDTO(
        String oldPassword,
        String newPassword
) {
}
