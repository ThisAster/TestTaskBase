package com.thisaster.testtask.auth.service;

import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.RoleEntity;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.mapper.UserMapper;
import com.thisaster.testtask.user.service.RoleService;
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
    private final RoleService roleService;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public String logIn(UserDTO userDTO) {
        var user = userDetailsService.loadUserByUsername(userDTO.getUsername());
        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            System.out.println(userDTO.getPassword());
            System.out.println(user.getPassword());
            throw new BadCredentialsException("Wrong password");
        }

        String jwt = jwtService.generateToken(user);
        log.debug("Пользователь {} успешно авторизирован", userDTO.getUsername());
        return jwt;
    }

    public void registerUser(UserDTO userDTO) {
        RoleEntity roleEntity = roleService.getByRoleName(userDTO.getRole());
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoleId(roleEntity.getId());
        userService.createUser(user);
    }
}
