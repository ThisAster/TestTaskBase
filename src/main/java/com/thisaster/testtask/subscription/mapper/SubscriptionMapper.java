package com.thisaster.testtask.subscription.mapper;

import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
import com.thisaster.testtask.subscription.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubscriptionMapper {

    SubscriptionDTO toDTO(Subscription sub);

    Subscription toEntity(SubscriptionDTO subDTO);

    Set<SubscriptionDTO> toDTOSet(Set<Subscription> subscriptions);

    Set<Subscription> toEntitySet(Set<SubscriptionDTO> subscriptionDTOs);
}