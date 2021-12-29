package pl.polsl.reviewersapp.api.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.reviewersapp.api.model.dto.user.UserAddDTO;
import pl.polsl.reviewersapp.api.model.dto.user.UserChangePasswordDTO;
import pl.polsl.reviewersapp.api.model.dto.user.UserGetDTO;
import pl.polsl.reviewersapp.api.model.dto.user.UserUpdateDTO;
import pl.polsl.reviewersapp.api.model.entity.UserEntity;
import pl.polsl.reviewersapp.api.model.mapper.UserMapper;
import pl.polsl.reviewersapp.api.model.repo.UserRepo;
import pl.polsl.reviewersapp.api.security.JwtUtils;
import pl.polsl.reviewersapp.api.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public UserGetDTO get(Long id) {
        return UserMapper.INSTANCE.toGetDTO(userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("User id %d does not exist", id))));
    }

    @Override
    public List<UserGetDTO> getAll() {
        return UserMapper.INSTANCE.toGetDTOList(userRepo.findAll());
    }

    @Override
    public UserGetDTO add(UserAddDTO input) {
        return UserMapper.INSTANCE.toGetDTO(userRepo.save(UserEntity.builder()
                .username(input.username())
                .password(passwordEncoder.encode(input.password()))
                .build()));
    }

    @Override
    @Transactional
    public UserGetDTO update(UserUpdateDTO input) {
        UserEntity userEntity = userRepo.findById(input.id())
                .orElseThrow(() -> new NoSuchElementException(String.format("User id %d does not exist", input.id())));

        if(input.username() != null)
            userEntity.setUsername(input.username());
        if(input.password() != null)
            userEntity.setPassword(passwordEncoder.encode(input.password()));

        return UserMapper.INSTANCE.toGetDTO(userEntity);
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void changePassword(UserChangePasswordDTO input, String authHeader) throws IllegalAccessException {
        Long userId = jwtUtils.getUserId(authHeader.substring(7));
        UserEntity userEntity = userRepo.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("User id %d does not exist", userId)));
        if (!passwordEncoder.matches(input.oldPassword(), userEntity.getPassword()))
            throw new IllegalAccessException("Old password is incorrect");
        userEntity.setPassword(passwordEncoder.encode(input.newPassword()));
    }
}
