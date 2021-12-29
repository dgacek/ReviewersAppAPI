package pl.polsl.reviewersapp.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.polsl.reviewersapp.api.model.dto.user.UserChangePasswordDTO;
import pl.polsl.reviewersapp.api.service.UserService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public record UserController(
        UserService userService
) {
    @PutMapping("/changepwd")
    public ResponseEntity<Void> changePassword(@RequestBody UserChangePasswordDTO input, @RequestHeader("Authorization") String authHeader) {
        try {
            userService.changePassword(input, authHeader);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }
}
