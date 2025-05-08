package com.thisaster.testtask.user.service;

import com.thisaster.testtask.auth.exception.UserAlreadyExistException;
import com.thisaster.testtask.subscription.entity.Subscription;
import com.thisaster.testtask.subscription.repository.SubscriptionRepository;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final RoleService roleService;

    public User getUserByLogin(String login) {
        User user = userRepository.findByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setRole(roleService.getByRoleId(user.getRole().getId()));
        return user;
    }

    @Transactional
    public void createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistException("User " + user.getUsername() + " already exists");
        }
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void subscribeUserToSub(Long userId, Long subscriptionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (user.getSubscriptions() == null) user.setSubscriptions(new HashSet<>());
        if (subscription.getUsers() == null) subscription.setUsers(new HashSet<>());

        boolean addedToUser = user.getSubscriptions().add(subscription);
        boolean addedToSub  = subscription.getUsers().add(user);

        if (addedToUser || addedToSub) userRepository.save(user);
    }

    public void removeSubFromUser(Long userId, Long subscriptionId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        subscriptionRepository.deleteById(subscriptionId);
    }

    public Set<Subscription> getUserSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getSubscriptions();
    }

}
