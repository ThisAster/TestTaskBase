package com.thisaster.testtask.subscription.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thisaster.testtask.user.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Builder.Default
    private Set<UserDTO> users = new HashSet<>();
}
