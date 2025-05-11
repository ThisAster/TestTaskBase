package com.thisaster.testtask.user.controller;

import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
import com.thisaster.testtask.subscription.entity.Subscription;
import com.thisaster.testtask.subscription.mapper.SubscriptionMapper;
import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.mapper.UserMapper;
import com.thisaster.testtask.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final SubscriptionMapper subscriptionMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO result = userMapper.toDTO(user);
        result.setSubscriptions(subscriptionMapper.toDTOSet(user.getSubscriptions()));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id " + id + " successfully deleted.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User newUser = userMapper.toEntity(userDTO);
        newUser.setSubscriptions(subscriptionMapper.toEntitySet(userDTO.getSubscriptions()));
        userService.updateUser(id, newUser);
        UserDTO result = userMapper.toDTO(userService.getUserById(id));
        result.setSubscriptions(subscriptionMapper.toDTOSet(newUser.getSubscriptions()));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/subscriptions")
    public ResponseEntity<String> addSubscription(@PathVariable Long id, @RequestBody SubscriptionDTO subscriptionDTO) {
        User user = userService.getUserById(id);
        Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO);
        subscription.setUsers(userMapper.mapUsersDto(subscriptionDTO.getUsers()));
        userService.subscribeUserToSub(user.getId(), subscription);
        return ResponseEntity.ok("User with id "
                + user.getId()
                + " subscribe to: "
                + subscriptionDTO.getName());
    }

    @GetMapping("/{id}/subscriptions")
    public ResponseEntity<Set<SubscriptionDTO>> getSubscriptions(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionMapper.toDTOSet(userService.getUserSubscriptions(id)));
    }

    @DeleteMapping("/{id}/subscriptions/{sub_id}")
    public ResponseEntity<String> deleteSubscription(@PathVariable Long id, @PathVariable Long sub_id) {
        userService.removeSubFromUser(id, sub_id);
        return ResponseEntity.ok("User with id "
                + id
                + " successfully deleted subscription with id "
                + sub_id);
    }

}
