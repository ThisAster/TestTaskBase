package com.thisaster.testtask.user.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public enum Role {
    ADMIN("ADMIN"),
    USER("USER"),
    SUPERVISOR("SUPERVISOR");

    private String string;
}