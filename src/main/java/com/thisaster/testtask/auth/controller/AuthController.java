package com.thisaster.testtask.auth.controller;

import com.thisaster.testtask.auth.config.UserPrincipal;
import com.thisaster.testtask.auth.service.AuthService;
import com.thisaster.testtask.auth.service.JwtService;
import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Validated UserDTO request) {
        var authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword());

        authenticationManager.authenticate(authentication);

        User user = userService.getUserByLogin(request.getUsername());
        String jwt = jwtService.generateToken(new UserPrincipal(user));
        log.debug("Пользователь {} успешно авторизирован", request.getUsername());
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwt)
                .build();
    }

    @PreAuthorize("hasRole('SUPERVISOR')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated UserDTO request) {
        authService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
