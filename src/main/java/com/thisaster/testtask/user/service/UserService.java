package com.thisaster.testtask.user.service;

import com.thisaster.testtask.subscription.entity.Subscription;
import com.thisaster.testtask.subscription.repository.SubscriptionRepository;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public User getUserByLogin(String login) {
        User user = userRepository.findByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + login));
        user.setRole(roleService.getByRoleId(user.getRoleId()));
        return user;
    }

    @Transactional
    public void createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("User " + user.getUsername() + " already exists");
        }
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setSubscriptions(user.getSubscriptions());
        userRepository.save(existingUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void subscribeUserToSub(Long userId, Subscription subscription) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean addedToUser = user.getSubscriptions().add(subscription);
        boolean addedToSub  = subscription.getUsers().add(user);

        if (addedToUser || addedToSub) userRepository.save(user);
    }

    public void removeSubFromUser(Long userId, Long subscriptionId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        user.getSubscriptions().remove(subscription);
        userRepository.save(user);
    }

    public Set<Subscription> getUserSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getSubscriptions();
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }

}
