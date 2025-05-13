package com.thisaster.testtask.auth.service;

import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.RoleEntity;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.mapper.UserMapper;
import com.thisaster.testtask.user.service.RoleService;
import com.thisaster.testtask.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(UserDTO userDTO) {
        RoleEntity roleEntity = roleService.getByRoleName(userDTO.getRole());
        User user = userMapper.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoleId(roleEntity.getId());
        userService.createUser(user);
    }
}
