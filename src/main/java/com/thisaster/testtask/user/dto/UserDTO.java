package com.thisaster.testtask.subscription.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String username;
    private String email;
}
