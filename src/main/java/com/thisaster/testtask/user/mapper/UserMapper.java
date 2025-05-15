package com.thisaster.testtask.user.mapper;

import com.thisaster.testtask.subscription.mapper.SubscriptionMapper;
import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {SubscriptionMapper.class, RoleMapper.class})
public interface UserMapper {

    @Mapping(target = "role", qualifiedByName = "entityToString", source = "role")
    UserDTO toDTO(User user);

    @Mapping(target = "role", qualifiedByName = "stringToEntity", source = "role")
    @Mapping(target = "roleId", expression = "java(roleMapper.fromString(userDTO.getRole()).getId())")
    User toEntity(UserDTO userDTO);
}