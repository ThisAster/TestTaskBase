package com.thisaster.testtask.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
import com.thisaster.testtask.user.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<SubscriptionDTO> subscriptions;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Pattern(regexp = "^(ADMIN|USER)$")
    private String role;
}
