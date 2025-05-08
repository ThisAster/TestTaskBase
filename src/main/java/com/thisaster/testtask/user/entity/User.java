package com.thisaster.testtask.user.entity;

import com.thisaster.testtask.subscription.entity.Subscription;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String email;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_subscription",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    private Set<Subscription> subscriptions;

    @Column(name = "role_id")
    private long roleId;

    @Transient
    private RoleEntity role;
}
