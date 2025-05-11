package com.thisaster.testtask.subscription.mapper;

import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
import com.thisaster.testtask.subscription.entity.Subscription;
import com.thisaster.testtask.user.mapper.UserMapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface SubscriptionMapper {

    SubscriptionMapper INSTANCE = Mappers.getMapper(SubscriptionMapper.class);

    @Mapping(target = "users", source = "users", qualifiedByName = "mapUsers")
    SubscriptionDTO toDTO(Subscription sub);

    @Mapping(target = "users", source = "users", qualifiedByName = "mapUsersDto")
    Subscription toEntity(SubscriptionDTO subDTO);

    @IterableMapping(elementTargetType = SubscriptionDTO.class)
    Set<SubscriptionDTO> toDTOSet(Set<Subscription> subscriptions);

    @IterableMapping(elementTargetType = Subscription.class)
    Set<Subscription> toEntitySet(Set<SubscriptionDTO> subscriptionDTOs);
}
