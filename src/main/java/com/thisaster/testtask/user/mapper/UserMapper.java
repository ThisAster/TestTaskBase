package com.thisaster.testtask.user.mapper;

import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserMapper {
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    public abstract UserDTO toDTO(User user);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    public abstract User toEntity(UserDTO userDTO);

    public abstract Set<UserDTO> mapUsers(Set<User> users);

    public abstract Set<User> mapUsersDto(Set<UserDTO> userDTOs);
}
