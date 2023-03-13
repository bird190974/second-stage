package com.bird.dto;

import com.bird.entity.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserFilter {
    String firstName;
    String lastName;
    String email;
    String password;
    Role role;
}
