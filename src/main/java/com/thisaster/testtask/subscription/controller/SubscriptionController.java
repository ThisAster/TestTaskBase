package com.thisaster.testtask.subscription.controller;

import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
import com.thisaster.testtask.subscription.mapper.SubscriptionMapper;
import com.thisaster.testtask.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    public final SubscriptionService subscriptionService;
    public final SubscriptionMapper subscriptionMapper;

    @GetMapping("/top")
    public ResponseEntity<Set<SubscriptionDTO>> getTopThreeSubscriptions() {
        Set<SubscriptionDTO> subDTOsTop = subscriptionMapper
                .toDTOSet(subscriptionService.getTopThreeSubscriptions());

        return ResponseEntity.ok(subDTOsTop);
    }
}
