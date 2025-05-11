package com.thisaster.testtask.user.service;

import com.thisaster.testtask.user.entity.RoleEntity;
import com.thisaster.testtask.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleEntity getByRoleId(Long roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    public RoleEntity getByRoleName(String roleName) {
        return roleRepository.findByName(roleName).orElse(null);
    }
}
