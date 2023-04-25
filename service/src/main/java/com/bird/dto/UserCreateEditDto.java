package com.bird.dto;

import com.bird.entity.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateEditDto {
    // TODO: 25.04.2023 add validation 
    String firstName;
    String lastName;
    String email;
    String rawPassword;
    Role role;
    Integer clientId;
}
