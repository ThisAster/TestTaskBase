package com.thisaster.testtask.auth.service;

import com.thisaster.testtask.auth.dto.AuthDTO;
import com.thisaster.testtask.auth.util.JwtUtil;
import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.mapper.UserMapper;
import com.thisaster.testtask.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserService userService;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public String logIn(AuthDTO authDTO) {
        var user = userDetailsService.loadUserByUsername(authDTO.getUsername());
        if (!passwordEncoder.matches(authDTO.getPassword(), user.getPassword())) {
            System.out.println(authDTO.getPassword());
            System.out.println(user.getPassword());
            throw new BadCredentialsException("Wrong password");
        }

        String jwt = jwtUtil.generateToken(user);
        log.debug("Пользователь {} успешно авторизирован", authDTO.getUsername());
        return jwt;
    }

    public void registerUser(AuthDTO authDTO) {
        authDTO.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        System.out.println("AuthDTO: " + authDTO.getPassword());

        UserDTO userDTO = UserDTO.builder()
                .username(authDTO.getUsername())
                .password(authDTO.getPassword())
                .email(authDTO.getEmail())
                .build();

        User user = userMapper.toEntity(userDTO);
        user.setRoles(authDTO.getRoles());
        userService.createUser(user);
    }
}
