package pl.polsl.reviewersapp.api.service;

import pl.polsl.reviewersapp.api.model.dto.user.UserAddDTO;
import pl.polsl.reviewersapp.api.model.dto.user.UserGetDTO;
import pl.polsl.reviewersapp.api.model.dto.user.UserUpdateDTO;

import java.util.List;

public interface UserService {
    UserGetDTO get(Long id);
    List<UserGetDTO> getAll();
    UserGetDTO add(UserAddDTO input);
    UserGetDTO update(UserUpdateDTO input);
    void delete(Long id);
}
