package pl.polsl.reviewersapp.api.model.dto.user;

import lombok.Builder;

@Builder
public record UserAddDTO(
        String username,
        String password
) {
}
