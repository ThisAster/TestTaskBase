package com.thisaster.testtask.subscription.mapper;

import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
import com.thisaster.testtask.subscription.entity.Subscription;
import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.mapper.UserMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    @Mapping(target = "users", source = "users", qualifiedByName = "mapUsers")
    SubscriptionDTO toDTO(Subscription sub);

    @Mapping(target = "users", source = "users", qualifiedByName = "mapUsersDto")
    Subscription toEntity(SubscriptionDTO subDTO);
}
