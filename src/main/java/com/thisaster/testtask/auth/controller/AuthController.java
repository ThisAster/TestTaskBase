package com.thisaster.testtask.auth.controller;

import com.thisaster.testtask.auth.service.AuthService;
import com.thisaster.testtask.user.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Validated UserDTO request) {
        try {
            String jwt = authService.logIn(request);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + jwt)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Validated UserDTO request) {
        authService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }
}
