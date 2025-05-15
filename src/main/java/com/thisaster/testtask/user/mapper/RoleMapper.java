package com.thisaster.testtask.user.mapper;

import com.thisaster.testtask.user.entity.RoleEntity;
import com.thisaster.testtask.user.service.RoleService;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class RoleMapper {

    @Autowired
    private RoleService roleService;

    @Named("stringToEntity")
    public RoleEntity fromString(String roleName) {
        if (roleName == null) {
            return null;
        }
        return roleService.getByRoleName(roleName);
    }
    @Named("entityToString")
    public String toString(RoleEntity entity) {
        if (entity == null) {
            return null;
        }
        return entity.getName();
    }
}
