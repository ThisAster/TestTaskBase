//package com.thisaster.testtask.subscription.service;
//
//import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
//import com.thisaster.testtask.subscription.entity.Subscription;
//import com.thisaster.testtask.subscription.mapper.SubscriptionMapper;
//import com.thisaster.testtask.subscription.repository.SubscriptionRepository;
//import com.thisaster.testtask.user.entity.User;
//import com.thisaster.testtask.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.HashSet;
//
//@Service
//@RequiredArgsConstructor
//public class SubscriptionService {
//    private final SubscriptionRepository subscriptionRepository;
//    private final SubscriptionMapper subMapper = SubscriptionMapper.INSTANCE;
//
//    public Subscription createSubscription(SubscriptionDTO dto) {
//        Subscription subscription = subMapper.toEntity(dto);
//        return subscriptionRepository.save(subscription);
//    }
//}
