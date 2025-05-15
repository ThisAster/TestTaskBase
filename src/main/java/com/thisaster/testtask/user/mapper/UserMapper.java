package com.thisaster.testtask.user.mapper;

import com.thisaster.testtask.subscription.mapper.SubscriptionMapper;
import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {SubscriptionMapper.class})
public abstract class UserMapper {

    @Mapping(target = "role", source = "role.name")
    public abstract UserDTO toDTO(User user);

    @Mapping(target = "role", ignore = true) // всё равно подставим вручную в контроллере
    @Mapping(target = "roleId", ignore = true) // подставим вручную
    public abstract User toEntity(UserDTO userDTO);
}