package com.thisaster.testtask.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thisaster.testtask.subscription.dto.SubscriptionDTO;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Set<SubscriptionDTO> subscriptions;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    @NotBlank(message = "Role cannot be blank")
    @Pattern(regexp = "^(ADMIN|USER|SUPERVISOR)$", message = "Role must be either admin or user")
    private String role;
}
