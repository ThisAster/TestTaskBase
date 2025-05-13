package com.thisaster.testtask.subscription.mapper;

import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
import com.thisaster.testtask.subscription.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class SubscriptionMapper {

    public abstract SubscriptionDTO toDTO(Subscription sub);

    @Mapping(target = "users", ignore = true)
    public abstract Subscription toEntity(SubscriptionDTO subDTO);

    public abstract Set<SubscriptionDTO> toDTOSet(Set<Subscription> subscriptions);

    public abstract Set<Subscription> toEntitySet(Set<SubscriptionDTO> subscriptionDTOs);
}