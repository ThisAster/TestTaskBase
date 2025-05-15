package com.thisaster.testtask.subscription.repository;

import com.thisaster.testtask.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query(value = "SELECT s.id as id, s.name as name, COUNT(u.id) as userCount " +
            "FROM subscriptions s " +
            "LEFT JOIN user_subscription us ON s.id = us.subscription_id " +
            "LEFT JOIN user_ u ON us.user_id = u.id " +
            "GROUP BY s.id, s.name " +
            "ORDER BY userCount DESC " +
            "LIMIT 3", nativeQuery = true)
    Set<Subscription> findTop3ByNameOrderByUsersCountDesc();

    @Query("SELECT s FROM Subscription s WHERE s.name = :name")
    Optional<Subscription> findByName(String name);

    boolean existsByName(String name);
}
