package com.thisaster.testtask.user.entity;

import com.thisaster.testtask.subscription.entity.Subscription;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_subscription",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    @Builder.Default
    private Set<Subscription> subscriptions = new HashSet<>();

    @Column(name = "role_id")
    private long roleId;

    @Transient
    private RoleEntity role;
}
