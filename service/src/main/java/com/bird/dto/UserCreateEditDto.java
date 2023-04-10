package com.bird.dto;

import com.bird.entity.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateEditDto {

    String firstName;
    String lastName;
    String email;
    String password;
    Role role;
    Integer clientId;
}
