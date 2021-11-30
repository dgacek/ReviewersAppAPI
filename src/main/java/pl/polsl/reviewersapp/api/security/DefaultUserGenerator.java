package pl.polsl.reviewersapp.api.security;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.polsl.reviewersapp.api.model.dto.user.UserAddDTO;
import pl.polsl.reviewersapp.api.service.UserService;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class DefaultUserGenerator {
    private final UserService userService;

    /**
     * Creates a default user (admin:admin) if no users exist
     */
    @PostConstruct
    private void createDefaultUser() {
        if (!userService.getAll().isEmpty())
            return;

        userService.add(new UserAddDTO("admin", "admin"));
    }
}
