package com.thisaster.testtask.subscription.repository;

import com.thisaster.testtask.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT s FROM Subscription s LEFT JOIN s.users u GROUP BY s.id ORDER BY COUNT(u) DESC LIMIT 3")
    Set<Subscription> findTop3ByOrderByUsersCountDesc();
}
