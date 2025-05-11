package com.thisaster.testtask.subscription.service;

import com.thisaster.testtask.subscription.entity.Subscription;
import com.thisaster.testtask.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    public Set<Subscription> getTopThreeSubscriptions() {
        return subscriptionRepository.findTop3ByOrderByUsersCountDesc();
    }
}
