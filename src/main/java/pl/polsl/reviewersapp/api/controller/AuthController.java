package pl.polsl.reviewersapp.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.reviewersapp.api.model.dto.auth.AuthRequestDTO;
import pl.polsl.reviewersapp.api.model.dto.auth.AuthResponseDTO;
import pl.polsl.reviewersapp.api.model.entity.UserEntity;
import pl.polsl.reviewersapp.api.security.JwtUtils;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password()));
            return new ResponseEntity<>(
                    new AuthResponseDTO(jwtUtils.generateAccessToken((UserEntity) authentication.getPrincipal())),
                    HttpStatus.OK);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
