package com.thisaster.testtask.subscription.entity;

import com.thisaster.testtask.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany(mappedBy = "subscriptions")
    @Builder.Default
    private Set<User> users = new HashSet<>();
}
