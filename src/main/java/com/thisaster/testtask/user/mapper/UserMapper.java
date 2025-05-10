package com.thisaster.testtask.user.mapper;

import com.thisaster.testtask.subscription.mapper.SubscriptionMapper;
import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "subscriptions", source = "subscriptions")
    @Mapping(target = "roles", ignore = true)
    UserDTO toDTO(User user);

    @Mapping(target = "subscriptions", source = "subscriptions")
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserDTO userDTO);

    @Named("mapUsers")
    default Set<UserDTO> mapUsers(Set<User> users) {
        return users.stream()
                .map(this::toDTO)
                .collect(java.util.stream.Collectors.toSet());
    }
    @Named("mapUsersDto")
    default Set<User> mapUsersDto(Set<UserDTO> userDTOs) {
        return userDTOs.stream()
                .map(this::toEntity)
                .collect(java.util.stream.Collectors.toSet());
    }
}
