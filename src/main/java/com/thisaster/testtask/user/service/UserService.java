package com.thisaster.testtask.user.service;

import com.thisaster.testtask.exception.EntityAlreadyExistsException;
import com.thisaster.testtask.exception.EntityNotFoundException;
import com.thisaster.testtask.subscription.entity.Subscription;
import com.thisaster.testtask.subscription.repository.SubscriptionRepository;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RoleService roleService;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserByLogin(String login) {
        User user = userRepository.findByUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with name: " + login));
        user.setRole(roleService.getByRoleId(user.getRoleId()));
        return user;
    }

    @Transactional
    public void createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new EntityAlreadyExistsException("User " + user.getUsername() + " already exists");
        }
        userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        existingUser.setRole(roleService.getByRoleId(user.getRoleId()));


        Set<Subscription> currentSubscriptions = new HashSet<>(existingUser.getSubscriptions());
        if (user.getSubscriptions() != null) {
            for (Subscription newSub : user.getSubscriptions()) {
                boolean alreadySubscribed = currentSubscriptions.stream()
                        .anyMatch(existingSub -> existingSub.getName().equals(newSub.getName()));

                if (!alreadySubscribed) {
                    subscribeUserToSub(userId, newSub);
                }
            }
        }

        Set<Subscription> updatedSubscriptions = existingUser.getSubscriptions();
        for (Subscription currentSub : currentSubscriptions) {
            if (!updatedSubscriptions.contains(currentSub)) {
                removeSubFromUser(userId, currentSub.getId());
            }
        }

        return existingUser;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public void subscribeUserToSub(Long userId, Subscription subscription) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Subscription existingSubscription = subscriptionRepository.findByName(subscription.getName()).orElse(null);

        if (existingSubscription == null) {
            subscriptionRepository.save(subscription);
            existingSubscription = subscription;
        }

        boolean addedToUser = user.getSubscriptions().add(existingSubscription);
        boolean addedToSub = existingSubscription.getUsers().add(user);

        if (addedToUser || addedToSub) {
            userRepository.save(user);
            subscriptionRepository.save(existingSubscription);
        }
    }

    @Transactional
    public void removeSubFromUser(Long userId, Long subscriptionId) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new EntityNotFoundException("User not found with id: " + userId));

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found with id: " + subscriptionId));

        subscription.getUsers().remove(user);
        if (subscription.getUsers().isEmpty()) {
            subscriptionRepository.delete(subscription);
        }
        user.getSubscriptions().remove(subscription);
        userRepository.save(user);
    }

    public Set<Subscription> getUserSubscriptions(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        return subscriptionRepository.findSubscriptionsByUser(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }

}
