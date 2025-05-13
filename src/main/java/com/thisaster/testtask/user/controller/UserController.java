package com.thisaster.testtask.user.controller;

import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
import com.thisaster.testtask.subscription.entity.Subscription;
import com.thisaster.testtask.subscription.mapper.SubscriptionMapper;
import com.thisaster.testtask.user.dto.UserDTO;
import com.thisaster.testtask.user.entity.User;
import com.thisaster.testtask.user.mapper.UserMapper;
import com.thisaster.testtask.user.service.RoleService;
import com.thisaster.testtask.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final SubscriptionMapper subscriptionMapper;
    private final RoleService roleService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userMapper.toDTO(user);
        userDTO.setSubscriptions(subscriptionMapper.toDTOSet(user.getSubscriptions()));
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with id " + id + " successfully deleted.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody @Validated UserDTO userDTO) {
        User newUser = userMapper.toEntity(userDTO);
        Set<Subscription> subscriptions = subscriptionMapper.toEntitySet(userDTO.getSubscriptions());
        newUser.setSubscriptions(subscriptions);
        newUser.setRole(roleService.getByRoleName(userDTO.getRole()));
        newUser.setRoleId(newUser.getRoleId());
        userService.updateUser(id, newUser);
        UserDTO modificationUser = userMapper.toDTO(userService.getUserById(id));
        return ResponseEntity.ok(modificationUser);
    }

    @PostMapping("/{id}/subscriptions")
    public ResponseEntity<String> addSubscription(@PathVariable Long id, @RequestBody @Validated SubscriptionDTO subscriptionDTO) {
        User user = userService.getUserById(id);
        Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO);
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
