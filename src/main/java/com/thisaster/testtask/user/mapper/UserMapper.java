package com.thisaster.testtask.user.mapper;

import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "role", ignore = true)
    UserDTO toDTO(User user);

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "roleId", ignore = true)
    User toEntity(UserDTO userDTO);

    Set<UserDTO> mapUsers(Set<User> users);

    Set<User> mapUsersDto(Set<UserDTO> userDTOs);
}
