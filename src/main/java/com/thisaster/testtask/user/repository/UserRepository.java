package com.thisaster.testtask.user.repository;

import com.thisaster.testtask.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    int countBySubscriptions_Id(Long subscriptionId);
}
